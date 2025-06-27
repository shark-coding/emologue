package com.project.emologue.controller;

import com.project.emologue.model.admin.Admin;
import com.project.emologue.model.admin.AdminAuthenticationResponse;
import com.project.emologue.model.admin.AdminLoginRequestBody;
import com.project.emologue.model.admin.AdminSignUpRequestBody;
import com.project.emologue.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired private AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> signUp(
            @Valid @RequestBody AdminSignUpRequestBody adminSignUpRequestBody) {
        var admin = adminService.signUp(adminSignUpRequestBody);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AdminAuthenticationResponse> authenticate(
            @Valid @RequestBody AdminLoginRequestBody adminLoginRequestBody) {
        var response = adminService.authenticate(adminLoginRequestBody);
        return ResponseEntity.ok(response);
    }


}
