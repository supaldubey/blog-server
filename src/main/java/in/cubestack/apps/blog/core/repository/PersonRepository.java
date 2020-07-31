package in.cubestack.apps.blog.core.repository;


import in.cubestack.apps.blog.core.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;

@Singleton
public class PersonRepository implements PanacheRepository<Person> {

}
