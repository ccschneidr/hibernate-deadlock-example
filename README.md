# Pessimistic Deadlocks with Optimistic Locking in Hibernate
This is a small example project to show that deadlock from pessimistic locks occur, when using optimistic locking (@Version) with Hibernate in parallel sessions.

## Reproduce
- make sure you have git, Java 17 and Maven installed and execute

```bash
git clone https://github.com/ccschneidr/hibernate-deadlock-example.git
cd hibernate-deadlock-example
mvn test
```
The test will print an error about a deadlock:

```
Deadlock detected. The current transaction was rolled back. Details: "APPLICATION_USER"; SQL statement:
...
```
