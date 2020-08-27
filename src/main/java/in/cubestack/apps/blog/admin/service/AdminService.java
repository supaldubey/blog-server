package in.cubestack.apps.blog.admin.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.TokenAuthenticationService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdminService {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final PersonService personService;

    public AdminService(TokenAuthenticationService tokenAuthenticationService, PersonService personService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.personService = personService;
    }

    public String login(String username, String password) {
        Person person = personService.findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException("Invalid username / password"));

        if (person.isPasswordValid(password)) {
            return tokenAuthenticationService.generateToken(person);
        }
        throw new RuntimeException("Invalid username / password");
    }

    public void resetPassword(String username, String password) {
        Person person = personService.findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException("Invalid username / password"));

        person.updatePassword(password);
    }
}
