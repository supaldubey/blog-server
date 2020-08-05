package in.cubestack.apps.blog.core.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PersonRole;
import in.cubestack.apps.blog.core.domain.PersonStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true, value = { "password" })
public class PersonCandidate {

    private long id;

    @NotBlank(message = "First name must not be blank")
    @Length(message = "First name must be between 2 to 200 characters", min = 2, max = 200)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Length(message = "Last name must be between 2 to 200 characters", min = 2, max = 200)
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be in valid format like abc@email.com")
    @Length(message = "Last name must be less than 200 characters", max = 200)
    private String email;

    @NotBlank(message = "Username must not be blank")
    @Length(message = "Username must be between 2 to 200 characters", min = 2, max = 200)
    private String username;

    @JsonIgnore
    @NotBlank(message = "Password must not be blank")
    @Length(message = "Password must be between 2 to 200 characters", min = 2, max = 200)
    private String password;

    @Length(message = "Phone must be less than 2o characters", max = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    @NotEmpty(message = "Roles must not be empty")
    @Valid
    private Set<RoleCandidate> roles = new TreeSet<>();

    public static PersonCandidate from(Person person) {
        PersonCandidate candidate = new PersonCandidate();
        candidate.setFirstName(person.getFirstName());
        candidate.setLastName(person.getLastName());
        candidate.setEmail(person.getEmail());
        candidate.setUsername(person.getUsername());
        candidate.setPhone(person.getPhone());
        candidate.setStatus(person.getStatus());
        candidate.setRoles(person.getRoles().stream().map(o -> RoleCandidate.from(o)).collect(Collectors.toCollection(TreeSet::new)));
        return candidate;
    }

    public Person toPerson() {
        Person person = new Person();
        person.setId(getId());
        person.setFirstName(getFirstName());
        person.setLastName(getLastName());
        person.setEmail(getEmail());
        person.setUsername(getUsername());
        person.setPassword(getPassword());
        person.setPhone(getPhone());
        person.setPersonRoles(getRoles().stream().map(o -> new PersonRole(person, o.toRole())).collect(Collectors.toList()));
        return person;
    }
}
