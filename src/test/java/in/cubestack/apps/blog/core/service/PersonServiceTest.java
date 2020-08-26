package in.cubestack.apps.blog.core.service;


import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.repository.PersonRepository;
import in.cubestack.apps.blog.core.resource.PersonCandidate;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class PersonServiceTest {

    private PersonService personService;
    private MockPersonRepo mockPersonRepo;

    @BeforeEach
    public void setUp() throws Exception {
        mockPersonRepo = new MockPersonRepo();
        personService = new PersonService(mockPersonRepo);
    }

    @Test
    public void save() {
        PersonCandidate personCandidate = new PersonCandidate();
        personCandidate.setUsername("test");
        personCandidate.setFirstName("fname");
        personCandidate.setLastName("lname");
        personCandidate.setPassword("pass");

        Person person = personService.save(personCandidate);

        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getFirstName(), "fname");
        Assertions.assertEquals(person.getLastName(), "lname");
        Assertions.assertEquals(person.getUsername(), "test");
    }

    @Test
    public void update() {
        PersonCandidate personCandidate = new PersonCandidate();
        personCandidate.setUsername("test");
        personCandidate.setFirstName("fname");
        personCandidate.setLastName("lname");
        personCandidate.setPassword("pass");

        personService.update(personCandidate);

        Person person = mockPersonRepo.person;

        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getFirstName(), "fname");
        Assertions.assertEquals(person.getLastName(), "lname");
        // update does not update usernames
        Assertions.assertEquals(person.getUsername(), "username");
    }

    @Test
    public void findByUsername() {
        Optional<Person> person = personService.findByUsername("test");

        Assertions.assertTrue(person.isPresent());
    }

    @Test
    public void findByUsernameMissing() {
        Optional<Person> person = personService.findByUsername("non_test");

        Assertions.assertTrue(person.isEmpty());
    }

    @Test
    public void createPerson() {

        Person person = personService.createPerson("fname", "lname", "123", "hello");

        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getFirstName(), "fname");
        Assertions.assertEquals(person.getLastName(), "lname");
        // update does not update usernames
        Assertions.assertEquals(person.getUsername(), "hello");
    }
}

class MockPersonRepo extends PersonRepository {

    Person person;

    @Override
    public Optional<Person> findByIdOptional(Long aLong) {
        return Optional.of(new Person("firstName", "lastName", "username"));
    }

    @Override
    public PanacheQuery<Person> find(String query, Object... params) {
        PanacheQuery mockQuery = Mockito.mock(PanacheQuery.class);
        if (params.length == 0) {
            Mockito.when(mockQuery.firstResultOptional()).thenReturn(Optional.empty());
        } else if (params[0].equals("test")) {
            Mockito.when(mockQuery.firstResultOptional()).thenReturn(Optional.of(new Person("", "", "test")));
        }

        return mockQuery;
    }

    @Override
    public void persist(Person person) {
        this.person = person;
    }
}
