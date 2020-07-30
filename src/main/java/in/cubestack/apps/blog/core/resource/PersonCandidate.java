package in.cubestack.apps.blog.core.resource;

import in.cubestack.apps.blog.core.domain.Person;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PersonCandidate {

    private final String firstName;
    private final String lastName;

    public static PersonCandidate from(Person person) {
        return new PersonCandidate(person.getFirstName(), person.getLastName());
    }



    public PersonCandidate(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
