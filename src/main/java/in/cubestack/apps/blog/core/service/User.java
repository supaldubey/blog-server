package in.cubestack.apps.blog.core.service;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.List;

@RegisterForReflection
public class User implements Principal {
    private final Long personId;
    private final String userName;
    private final List<String> roles;


    public User(Long personId, String userName, List<String> roles) {
        this.personId = personId;
        this.userName = userName;
        this.roles = roles;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
