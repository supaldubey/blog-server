package in.cubestack.apps.blog.core.repository;


import in.cubestack.apps.blog.base.repository.BaseRepository;
import in.cubestack.apps.blog.core.domain.Person;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class PersonRepository extends BaseRepository<Person> {

    @Inject
    EntityManager entityManager;

    public PersonRepository(EntityManager entityManager) {
        super(entityManager, Person.class);
    }
}
