package in.cubestack.apps.blog.admin.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.service.AuthenticationService;
import in.cubestack.apps.blog.core.service.PersonService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AdminService {

    @Inject
    AuthenticationService authenticationService;

    @Inject
    PersonService personService;

    public String login(String username, String password) {
        Person person = personService.findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException("Invalid username / password"));

        if (password.equals(person.getPassword())) {
            return authenticationService.generateToken(person);
        }
        throw new RuntimeException("Invalid username / password");
    }
}
