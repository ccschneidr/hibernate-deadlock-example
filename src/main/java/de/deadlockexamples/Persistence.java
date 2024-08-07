package de.deadlockexamples;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Component
public class Persistence {
    @Transactional
    public <T> T transactional(Callable<T> callable) throws Exception {
        return callable.call();
    }
}
