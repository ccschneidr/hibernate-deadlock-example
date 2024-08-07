package de.deadlockexamples;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeadlockExamplesApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Persistence persistence;

    @Autowired
    EntityManager entityManager;

    @Test
    public void testDeadlock() throws Exception {
        Integer userId1 = persistence.transactional(() ->
                userRepository.save(new ApplicationUser()).getUserid());

        Integer userId2 = persistence.transactional(() ->
                userRepository.save(new ApplicationUser()).getUserid());

        Thread thread1 = new Thread(() -> {
            try {
                persistence.transactional(() -> {
                    ApplicationUser applicationUser1 = userRepository.findById(userId1)
							.orElseThrow(() -> new IllegalStateException("Previously created user not found"));
                    ApplicationUser applicationUser2 = userRepository.findById(userId2)
							.orElseThrow(() -> new IllegalStateException("Previously created user not found"));

                    entityManager.lock(applicationUser1, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                    entityManager.lock(applicationUser2, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                    return null;
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            try {
                persistence.transactional(() -> {
                    ApplicationUser applicationUser1 = userRepository.findById(userId1)
							.orElseThrow(() -> new IllegalStateException("Previously created user not found"));
                    ApplicationUser applicationUser2 = userRepository.findById(userId2)
							.orElseThrow(() -> new IllegalStateException("Previously created user not found"));

                    entityManager.lock(applicationUser2, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                    entityManager.lock(applicationUser1, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                    return null;
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, "thread2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
