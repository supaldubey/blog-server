package in.cubestack.apps.blog.core.resource;

import in.cubestack.apps.blog.core.domain.Role;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@RegisterForReflection
public class RoleCandidate implements Comparable<RoleCandidate> {

    @NotNull(message = "Role id must not be null")
    private Long roleId;
    private String roleName;

    public RoleCandidate(long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static RoleCandidate from(Role role) {
        return new RoleCandidate(role.getId(), role.getRoleName());
    }

    public Role toRole() {
        return new Role(getRoleId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleCandidate that = (RoleCandidate) o;
        return Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @Override
    public int compareTo(RoleCandidate o) {
        return this.getRoleName().compareTo(o.getRoleName());
    }
}
