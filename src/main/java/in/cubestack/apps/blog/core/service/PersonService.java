package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
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
}
