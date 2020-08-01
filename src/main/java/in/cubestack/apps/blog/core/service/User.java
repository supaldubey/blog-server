package in.cubestack.apps.blog.core.service;

import java.util.List;

public class User {
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
}
