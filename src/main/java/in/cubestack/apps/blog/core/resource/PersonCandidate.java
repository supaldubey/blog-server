package in.cubestack.apps.blog.core.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PersonStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.validator.constraints.Length;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true, value = {"password"})
public class PersonCandidate {

    private long id;

    @FormParam("firstName")
    @NotBlank(message = "First name must not be blank")
    @Length(message = "First name must be between 2 to 200 characters", min = 2, max = 200)
    private String firstName;

    @FormParam("lastName")
    @NotBlank(message = "Last name must not be blank")
    @Length(message = "Last name must be between 2 to 200 characters", min = 2, max = 200)
    private String lastName;

    @FormParam("email")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be in valid format like abc@email.com")
    @Length(message = "Last name must be less than 200 characters", max = 200)
    private String email;

    @NotBlank(message = "Username must not be blank")
    @FormParam("username")
    @Length(message = "Username must be between 2 to 200 characters", min = 2, max = 200)
    private String username;

    @JsonIgnore
    @FormParam("password")
    @NotBlank(message = "Password must not be blank")
    @Length(message = "Password must be between 2 to 200 characters", min = 2, max = 200)
    private String password;

    @Length(message = "Phone must be less than 2o characters", max = 20)
    @FormParam("phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    @NotEmpty(message = "Roles must not be empty")
    @Valid
    @FormParam("roles")
    private Set<RoleCandidate> roles = new TreeSet<>();

    public static PersonCandidate from(Person person) {
        PersonCandidate candidate = new PersonCandidate();
        candidate.setFirstName(person.getFirstName());
        candidate.setLastName(person.getLastName());
        candidate.setEmail(person.getEmail());
        candidate.setUsername(person.getUsername());
        candidate.setPhone(person.getPhone());
        candidate.setStatus(person.getStatus());
        candidate.setRoles(person.getRoles().stream().map(RoleCandidate::from).collect(Collectors.toCollection(TreeSet::new)));
        return candidate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonStatus getStatus() {
        return status;
    }

    public void setStatus(PersonStatus status) {
        this.status = status;
    }

    public Set<RoleCandidate> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleCandidate> roles) {
        this.roles = roles;
    }
}
