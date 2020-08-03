package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TokenAuthenticationServiceTest {

    private TokenAuthenticationService tokenAuthenticationService;

    @BeforeEach
    public void init() {
        tokenAuthenticationService = new TokenAuthenticationService();
        tokenAuthenticationService.jwtSecret = "Test";
    }

    @Test
    public void testDecodeWithRoles() {
        Person person = new Person("Supal", "Dubey", "supaldubey");
        person.addRole(new Role("Admin"));
        person.addRole(new Role("Stupid"));

        String token = tokenAuthenticationService.generateToken(person);

        Optional<User> optionalUser = tokenAuthenticationService.fromToken(token);
        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();

        assertEquals(user.getUserName(), "supaldubey");
        assertEquals(user.getRoles(), Arrays.asList("Admin", "Stupid"));
    }

    @Test
    public void testDecode() {
        String token = tokenAuthenticationService.generateToken(new Person("Supal", "Dubey", "supaldubey"));

        Optional<User> optionalUser = tokenAuthenticationService.fromToken(token);
        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();

        assertEquals(user.getUserName(), "supaldubey");
        assertEquals(user.getRoles(), new ArrayList<>());
    }

    @Test
    public void testSign() {
        String token = tokenAuthenticationService.generateToken(new Person("Supal", "Dubey", "supaldubey"));
        assertNotNull(token);
    }

}