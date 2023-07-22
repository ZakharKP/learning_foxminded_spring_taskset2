# Task 2.6 Spring Data JPA
Assignment

Rewrite the DAO layer. Use Spring Data JPA instead of Hibernate.

## Example:

````
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByGroupName(String name) throws SQLException;
}
```

## Important:

Your database structure should not be changed

Your tests should work with the new dao with minimal changes
