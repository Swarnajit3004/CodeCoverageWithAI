package com.supportmethods;

import com.beginproject.WiremockPostUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/Users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestParam String userName,
            @RequestParam String role,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mailId) throws Exception {

        WiremockPostUser.User user = new WiremockPostUser.User();
        String jsonResponse = user.createUserResponse(userName, role, firstName, lastName, mailId);
        return ResponseEntity.ok(jsonResponse);
    }
}
