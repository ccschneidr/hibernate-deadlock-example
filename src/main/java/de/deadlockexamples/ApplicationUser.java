package de.deadlockexamples;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class ApplicationUser
{
    @Id
    @GeneratedValue
    Integer userid;

    @Version
    int version;

    public Integer getUserid() {
        return userid;
    }
}
