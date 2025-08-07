package com.project.emologue.service;

import com.project.emologue.exception.admin.AdminAlreadyExistsException;
import com.project.emologue.exception.admin.AdminNotFoundException;
import com.project.emologue.model.admin.Admin;
import com.project.emologue.model.admin.AdminAuthenticationResponse;
import com.project.emologue.model.admin.AdminLoginRequestBody;
import com.project.emologue.model.admin.AdminSignUpRequestBody;
import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.repository.AdminEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired private AdminEntityRepository adminEntityRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getAdminEntityByUsername(username);
    }

    public Admin signUp(AdminSignUpRequestBody adminSignUpRequestBody) {
        adminEntityRepository
                .findByUsername(adminSignUpRequestBody.username())
                .ifPresent(
                        user -> {
                            throw new AdminAlreadyExistsException();
                        });

        var adminEntity = adminEntityRepository.save(
                AdminEntity.of(
                        adminSignUpRequestBody.username(),
                        passwordEncoder.encode(adminSignUpRequestBody.password())
                ));
        return Admin.from(adminEntity);

    }

    public AdminAuthenticationResponse authenticate(AdminLoginRequestBody adminLoginRequestBody) {
        var adminEntity = getAdminEntityByUsername(adminLoginRequestBody.username());

        if (passwordEncoder.matches(adminLoginRequestBody.password(), adminEntity.getPassword())) {
            var accessToken = jwtService.generateAccessToken(adminEntity);
            return new AdminAuthenticationResponse(accessToken);
        } else {
            throw new AdminNotFoundException();
        }
    }

    private AdminEntity getAdminEntityByUsername(String username) {
        return adminEntityRepository.findByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException(username));
    }

    public boolean isUsernameDuplicate(String username) {
        return adminEntityRepository.findByUsername(username).isPresent();
    }
}
