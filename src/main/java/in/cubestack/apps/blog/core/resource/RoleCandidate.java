package in.cubestack.apps.blog.core.resource;

import in.cubestack.apps.blog.core.domain.Role;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.validation.constraints.NotNull;


@RegisterForReflection
public class RoleCandidate implements Comparable<RoleCandidate> {

    @NotNull(message = "Role id must not be null")
    private Long roleId;

    private String roleName;

    public RoleCandidate(String roleId) {
        this.roleId = Long.valueOf(roleId);
    }

    public RoleCandidate() {
    }

    public RoleCandidate(String roleId, String roleName) {
        this(roleId);
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    public static RoleCandidate from(Role role) {
        return new RoleCandidate(String.valueOf(role.getId()), role.getRoleName());
    }

    public Role toRole() {
        return new Role(getRoleId());
    }


    @Override
    public int compareTo(RoleCandidate o) {
        return this.getRoleId().compareTo(o.getRoleId());
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}