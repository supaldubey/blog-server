package in.cubestack.apps.blog.core.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "personRole")
@SequenceGenerator(name = "default_gen", sequenceName = "personRole_id_seq", allocationSize = 1)
public class PersonRole extends BaseModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roleId")
    private Role role;

    PersonRole() {
    }

    PersonRole(Person person, Role role) {
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
