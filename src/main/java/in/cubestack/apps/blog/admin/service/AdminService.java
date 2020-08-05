package in.cubestack.apps.blog.admin.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.TokenAuthenticationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AdminService {

    @Inject
    TokenAuthenticationService tokenAuthenticationService;

    @Inject
    PersonService personService;

    public String login(String username, String password) {
        Person person = personService.findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException("Invalid username / password"));

        if (person.isPasswordValid(password)) {
            return tokenAuthenticationService.generateToken(person);
        }
        throw new RuntimeException("Invalid username / password");
    }
}
