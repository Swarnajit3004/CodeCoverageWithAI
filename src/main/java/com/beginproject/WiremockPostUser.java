package com.beginproject;

public class WiremockPostUser {

    private static final String SCIM_BASE_URL = "http://localhost:8765/alfresco/api/-default-/public/scim/v2/";

    public boolean callWireMock(String input) {
        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        return input.equals("reverse(input)");
    }
}
