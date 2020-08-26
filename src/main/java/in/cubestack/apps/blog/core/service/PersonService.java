package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PersonRole;
import in.cubestack.apps.blog.core.domain.Role;
import in.cubestack.apps.blog.core.repository.PersonRepository;
import in.cubestack.apps.blog.core.resource.PersonCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(PersonCandidate candidate) {
        LOGGER.info("Saving person {}", candidate);
        Person person = new Person(candidate.getFirstName(), candidate.getLastName(), candidate.getUsername());
        person.updatePassword(candidate.getPassword());

        personRepository.persist(person);

        LOGGER.info("Person saved {}", person.getId());
        return person;
    }

    public void update(PersonCandidate candidate) {
        Person person = personRepository.findByIdOptional(candidate.getId()).orElseThrow(() -> new RuntimeException("User not found with id " + candidate.getId()));
        person.setFirstName(candidate.getFirstName());
        person.setLastName(candidate.getLastName());
        person.setEmail(candidate.getEmail());
        person.setPhone(candidate.getPhone());
        person.getRoles().clear();
        person.setPersonRoles(candidate.getRoles().stream().map(o -> new PersonRole(person, o.toRole())).collect(Collectors.toList()));
        person.setUpdatedAt(LocalDateTime.now());
        personRepository.persist(person);
    }

    public List<PersonCandidate> findAll() {
        return personRepository.findAll().list().stream().map(PersonCandidate::from).collect(Collectors.toList());
    }

    public Optional<Person> findByUsername(String username) {
        Optional<Person> person = personRepository.find("username", username).firstResultOptional();
        person.ifPresent(p -> p.getRoles().forEach(Role::getRoleName));
        return person;
    }

    public Person createPerson(String firstName, String lastName, String password, String username) {
        Person person = new Person(firstName, lastName, username);
        person.updatePassword(password);

        personRepository.persist(person);
        return person;
    }
}
