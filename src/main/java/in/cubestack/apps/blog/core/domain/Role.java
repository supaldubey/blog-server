package in.cubestack.apps.blog.core.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@SequenceGenerator(name = "default_gen", sequenceName = "role_id_seq", allocationSize = 1)
public class Role extends BaseModel {

    @Column
    private String roleName;

    Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
