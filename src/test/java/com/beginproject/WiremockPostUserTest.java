package com.beginproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WiremockPostUserTest {

    @Test
    void TestPostUser() {
        WiremockPostUser wiremockPostUser = new WiremockPostUser();
        assertTrue(wiremockPostUser.callWireMock("reverse(input)"));
    }
}