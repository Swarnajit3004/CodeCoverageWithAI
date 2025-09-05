package com.beginproject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WiremockPostUser {

    private static final String SCIM_BASE_URL = "http://localhost:9999/v2/Users/";

    public static class User {

        @JsonProperty("id")
        private String id;
        @JsonProperty("username")
        private String username;
        @JsonProperty("role")
        private String role;
        @JsonProperty("firstname")
        private String firstname;
        @JsonProperty("lastname")
        private String lastname;
        @JsonProperty("email")
        private String email;

        public String createUserResponse(String userName, String role, String firstName, String lastName, String mailId) throws Exception {
            User user = new User();
//            user.id = (int)(Math.random() * 90000) + 10000 + "";
            user.username = userName;
            user.role = role;
            user.firstname = firstName;
            user.lastname = lastName;
            user.email = mailId;

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(user);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
