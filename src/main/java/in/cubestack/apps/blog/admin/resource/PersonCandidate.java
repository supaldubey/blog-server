package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.core.domain.Person;

public class PersonCandidate {

    private String firstName;
    private String lastName;

    public PersonCandidate() {
    }

    public PersonCandidate(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static PersonCandidate from(Person person) {
        return new PersonCandidate(person.getFirstName(), person.getLastName());
    }
}
