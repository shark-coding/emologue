package com.project.emologue.service;

import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.exception.user.UserAlreadyExistsException;
import com.project.emologue.exception.user.UserNotFoundException;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.entity.UserEntity;
import com.project.emologue.model.user.User;
import com.project.emologue.model.user.UserAuthenticationResponse;
import com.project.emologue.model.user.UserLoginRequestBody;
import com.project.emologue.model.user.UserSignUpRequestBody;
import com.project.emologue.repository.JobEntityRepository;
import com.project.emologue.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private UserEntity getUserEntityByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
