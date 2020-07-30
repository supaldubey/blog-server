package in.cubestack.apps.blog.core.domain;


import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;

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

    private String username;

    private String password;

    @Column
    private String phone;

    @Column
    private String countryCode;


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
