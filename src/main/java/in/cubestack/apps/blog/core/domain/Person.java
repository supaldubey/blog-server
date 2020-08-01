package in.cubestack.apps.blog.core.domain;


import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "person")
@SequenceGenerator(name = "default_gen", sequenceName = "person_id_seq", allocationSize = 1)
public class Person extends BaseModel {
    @Column
    @Basic(optional = false)
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String profileImage;

    @Column
    @Enumerated(EnumType.STRING)
    private PersonStatus status = PersonStatus.ACTIVE;

    @Column
    private String phone;

    @Column
    private String countryCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<PersonRole> personRoles = new ArrayList<>();

    Person() {
    }

    public Person(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Person(String firstName, String lastName, String username, String password, String email, String phone, String countryCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.countryCode = countryCode;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void deactivate() {
        this.status = PersonStatus.DISABLED;
    }

    public void activate() {
        this.status = PersonStatus.ACTIVE;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public PersonStatus getStatus() {
        return status;
    }

    public void addRole(Role role) {
        PersonRole personRole = new PersonRole(this, role);
        this.personRoles.add(personRole);
    }

    public List<Role> getRoles() {
        return personRoles.stream().map(PersonRole::getRole).collect(Collectors.toList());
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
