package in.cubestack.apps.blog.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "personRole")
@SequenceGenerator(name = "default_gen", sequenceName = "personRole_id_seq", allocationSize = 1)
@JsonIgnoreProperties({"person"})
public class PersonRole extends BaseModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roleId")
    private Role role;

    public PersonRole() {
    }

    public PersonRole(Person person, Role role) {
        this.person = person;
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public Role getRole() {
        return role;
    }
}
