package com.project.emologue.controller;

import com.project.emologue.model.error.ErrorResponse;
import com.project.emologue.model.user.User;
import com.project.emologue.model.user.UserAuthenticationResponse;
import com.project.emologue.model.user.UserLoginRequestBody;
import com.project.emologue.model.user.UserSignUpRequestBody;
import com.project.emologue.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "사용자 회원가입 및 로그인 관련 API")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping
    @Operation(summary = "사용자 회원가입", description = "사용자 계정 신규 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserSignUpRequestBody.class),
                            examples = @ExampleObject(value = "{\"userId\":\"3\", \"username\":\"sanga\", \"name\":\"KimSanga\", \"jobname\":\"backend\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"User already exists\"}")
                    ))
    })
    public ResponseEntity<User> signUp(
            @Parameter(description = "사용자 회원가입 요청 데이터", required = true)
            @Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody) {
        var user = userService.signUp(userSignUpRequestBody);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "사용자 로그인", description = "사용자 로그인 및 JWT 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW5nYSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTE5NTkwNjgsImV4cCI6MTc1MTk2OTg2OH0.wkcdsShEgJ3TqecJanmh4-YPczcosKOUBdBcwXR0f7I\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    )),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\", \"message\":\"User with username sanga1 not found\"}")
                    ))
    })
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @Parameter(description = "사용자 로그인 요청 데이터", required = true)
            @Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
        var response = userService.authenticate(userLoginRequestBody);
        return ResponseEntity.ok(response);
    }

}
