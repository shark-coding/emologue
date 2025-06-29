package com.project.emologue.service;

import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.exception.user.UserAlreadyExistsException;
import com.project.emologue.exception.user.UserNotAuthenticationException;
import com.project.emologue.exception.user.UserNotFoundException;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.entity.UserEntity;
import com.project.emologue.model.user.User;
import com.project.emologue.model.user.UserAuthenticationResponse;
import com.project.emologue.model.user.UserLoginRequestBody;
import com.project.emologue.model.user.UserSignUpRequestBody;
import com.project.emologue.repository.JobEntityRepository;
import com.project.emologue.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired private UserEntityRepository userEntityRepository;
    @Autowired private JobEntityRepository jobEntityRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserEntityByUsername(username);
    }

    public User signUp(UserSignUpRequestBody userSignUpRequestBody) {
        userEntityRepository
                .findByUsername(userSignUpRequestBody.username())
                .ifPresent(
                        user -> {
                            throw new UserAlreadyExistsException();
                        });

        JobEntity job = jobEntityRepository
                .findByJobname(userSignUpRequestBody.jobname())
                .orElseThrow(() -> new JobNotFoundException());

        var userEntity = userEntityRepository.save(
                UserEntity.of(
                        userSignUpRequestBody.username(),
                        passwordEncoder.encode(userSignUpRequestBody.password()),
                        userSignUpRequestBody.name(),
                        job));

        return User.from(userEntity);
    }

    public UserAuthenticationResponse authenticate(UserLoginRequestBody userLoginRequestBody) {
        var userEntity = getUserEntityByUsername(userLoginRequestBody.username());

        if (passwordEncoder.matches(userLoginRequestBody.password(), userEntity.getPassword())) {
            var accessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(accessToken);
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserEntity getUserEntityByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotAuthenticationException("인증되지 않은 사용자입니다.");
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof UserEntity)) {
            throw new UserNotAuthenticationException("사용자 정보를 가져올 수 없습니다.");
        }
        return (UserEntity) principal;
    }

    public UserDetails getCurrentUserAndAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotAuthenticationException("인증되지 않은 사용자입니다.");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserEntity) {
            return (UserEntity) principal;
        } else if (principal instanceof AdminEntity) {
            return (AdminEntity) principal;
        } else {
            throw new UserNotAuthenticationException("사용자 정보를 가져올 수 없습니다.");
        }
    }
}
