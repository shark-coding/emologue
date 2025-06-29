package com.project.emologue.service;

import com.project.emologue.exception.admin.AdminNotFoundException;
import com.project.emologue.model.admin.Admin;
import com.project.emologue.model.admin.AdminAuthenticationResponse;
import com.project.emologue.model.admin.AdminLoginRequestBody;
import com.project.emologue.model.admin.AdminSignUpRequestBody;
import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.repository.AdminEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock private AdminEntityRepository adminEntityRepository;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks private AdminService adminService;

    private AdminSignUpRequestBody signUpRequest;
    private AdminLoginRequestBody loginRequest;
    private AdminEntity adminEntity;

    @BeforeEach
    void setUp() {
        signUpRequest = new AdminSignUpRequestBody("admin", "1234");
        loginRequest = new AdminLoginRequestBody("admin", "1234");
        adminEntity = AdminEntity.of(
                signUpRequest.username(),
                signUpRequest.password());
        adminEntity.setPassword("encodedPassword");
    }

    @Test
    void 관리자_회원가입_성공() {
        when(adminEntityRepository.save(any(AdminEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bCryptPasswordEncoder.encode(any(CharSequence.class)))
                .thenReturn("encodedPassword");

        Admin savedAdmin = adminService.signUp(signUpRequest);

        assertEquals("admin", savedAdmin.username());
    }

    @Test
    void 로그인_성공() {
        when(adminEntityRepository.findByUsername("admin")).thenReturn(Optional.of(adminEntity));
        when(bCryptPasswordEncoder.matches("1234", "encodedPassword")).thenReturn(true);
        when(jwtService.generateAccessToken(adminEntity)).thenReturn("accessToken");

        AdminAuthenticationResponse response = adminService.authenticate(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.accessToken());
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        when(adminEntityRepository.findByUsername("admin")).thenReturn(Optional.of(adminEntity));
        when(bCryptPasswordEncoder.matches("1234", "encodedPassword")).thenReturn(false);

        assertThrows(AdminNotFoundException.class, () -> adminService.authenticate(loginRequest));
    }

    @Test
    void 로그인_실패_사용자없음 () {
        when(adminEntityRepository.findByUsername("admin")).thenReturn(Optional.empty());

        assertThrows(AdminNotFoundException.class, () -> adminService.authenticate(loginRequest));
    }
}
