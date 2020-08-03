package in.cubestack.apps.blog.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void init() {
        person = new Person("Supal", "Dubey", "supaldubey");
    }

    @Test
    public void testPasswordEncryption() {
        boolean valid = person.isPasswordValid("");
        assertFalse(valid);
    }

    @Test
    public void testPasswordEncryptionWithPassword() {
        person.updatePassword("test");
        boolean valid = person.isPasswordValid("test");
        assertTrue(valid);
    }

    @Test
    public void testPasswordEncryptionWithSpecialChars() {
        person.updatePassword("$%^&*test");
        boolean valid = person.isPasswordValid("$%^&*test");
        assertTrue(valid);
    }
}