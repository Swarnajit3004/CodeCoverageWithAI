package com.beginproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WiremockPostUserTest {

    @Test
    void testCreateUserResponseContainsAllFields() throws Exception {
        WiremockPostUser.User user = new WiremockPostUser.User();
        String json = user.createUserResponse("user1", "admin", "John", "Doe", "john.doe@example.com");
        assertTrue(json.contains("\"username\":\"user1\""));
        assertTrue(json.contains("\"role\":\"admin\""));
        assertTrue(json.contains("\"firstname\":\"John\""));
        assertTrue(json.contains("\"lastname\":\"Doe\""));
        assertTrue(json.contains("\"email\":\"john.doe@example.com\""));
    }

    @Test
    void testSettersAndGetters() {
        WiremockPostUser.User user = new WiremockPostUser.User();
        user.setUsername("user2");
        user.setRole("user");
        user.setFirstname("Jane");
        user.setLastname("Smith");
    }
}