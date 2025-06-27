package com.project.emologue.controller;

import com.project.emologue.model.user.User;
import com.project.emologue.model.user.UserAuthenticationResponse;
import com.project.emologue.model.user.UserLoginRequestBody;
import com.project.emologue.model.user.UserSignUpRequestBody;
import com.project.emologue.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity<User> signUp(
            @Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody) {
        var user = userService.signUp(userSignUpRequestBody);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
        var response = userService.authenticate(userLoginRequestBody);
        return ResponseEntity.ok(response);
    }

}
