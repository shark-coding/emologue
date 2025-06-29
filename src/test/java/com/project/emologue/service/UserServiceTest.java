package com.project.emologue.service;

import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.exception.user.UserNotFoundException;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.entity.UserEntity;
import com.project.emologue.model.user.User;
import com.project.emologue.model.user.UserAuthenticationResponse;
import com.project.emologue.model.user.UserLoginRequestBody;
import com.project.emologue.model.user.UserSignUpRequestBody;
import com.project.emologue.repository.JobEntityRepository;
import com.project.emologue.repository.UserEntityRepository;
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
public class UserServiceTest {
    @Mock private UserEntityRepository userEntityRepository;
    @Mock private JobEntityRepository jobEntityRepository;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks private UserService userService;

    private UserSignUpRequestBody signUpRequest;
    private UserLoginRequestBody loginRequest;
    private JobEntity job;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        job = JobEntity.of("backend", "백엔드 엔지니어");
        signUpRequest = new UserSignUpRequestBody("sanga", "1234", "Kim", "backend");
        loginRequest = new UserLoginRequestBody("sanga", "1234");
        userEntity = UserEntity.of(
                signUpRequest.username(),
                signUpRequest.password(),
                signUpRequest.name(),
                job);
        userEntity.setPassword("encodedPassword");
    }

    @Test
    void 사용자_회원가입_성공() {
        when(jobEntityRepository.findByJobname("backend")).thenReturn(Optional.of(job));
        when(userEntityRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bCryptPasswordEncoder.encode(any(CharSequence.class)))
                .thenReturn("encodedPassword");

        User savedUser = userService.signUp(signUpRequest);

        assertEquals("sanga", savedUser.username());
        assertEquals("Kim", savedUser.name());
        assertEquals("backend", savedUser.jobname());
    }

    @Test
    void 직업이_없으면_예외발생() {
        when(jobEntityRepository.findByJobname("backend")).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> {
            userService.signUp(signUpRequest);
        });
    }

    @Test
    void 로그인_성공() {
        when(userEntityRepository.findByUsername("sanga")).thenReturn(Optional.of(userEntity));
        when(bCryptPasswordEncoder.matches("1234", "encodedPassword")).thenReturn(true);
        when(jwtService.generateAccessToken(userEntity)).thenReturn("accessToken");

        UserAuthenticationResponse response = userService.authenticate(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.accessToken());
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        when(userEntityRepository.findByUsername("sanga")).thenReturn(Optional.of(userEntity));
        when(bCryptPasswordEncoder.matches("1234", "encodedPassword")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.authenticate(loginRequest));
    }

    @Test
    void 로그인_실패_사용자없음 () {
        when(userEntityRepository.findByUsername("sanga")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.authenticate(loginRequest));
    }
}
