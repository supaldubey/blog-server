package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(Person person) {
        personRepository.persist(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll().list();
    }

    public Optional<Person> findByUsername(String username) {
        Optional<Person> person = personRepository.find("username", username).firstResultOptional();
        person.ifPresent(p -> p.getRoles().size());
        return person;
    }
}
