package de.deadlockexamples;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<ApplicationUser, Integer>
{

}
