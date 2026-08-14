package com.example.istream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordHasherTest {
    @Test
    public void hashesAndVerifiesPassword() {
        String password = "student-password";
        String hash = PasswordHasher.hash(password);

        assertNotEquals(password, hash);
        assertTrue(PasswordHasher.isHash(hash));
        assertTrue(PasswordHasher.verify(password, hash));
        assertFalse(PasswordHasher.verify("wrong-password", hash));
    }
}
