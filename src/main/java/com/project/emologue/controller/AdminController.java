package com.project.emologue.controller;

import com.project.emologue.model.admin.Admin;
import com.project.emologue.model.admin.AdminAuthenticationResponse;
import com.project.emologue.model.admin.AdminLoginRequestBody;
import com.project.emologue.model.admin.AdminSignUpRequestBody;
import com.project.emologue.model.error.ErrorResponse;
import com.project.emologue.model.user.User;
import com.project.emologue.service.AdminService;
import com.project.emologue.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Admin API", description = "관리자 회원가입 및 로그인 관련 API")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private UserService userService;

    @PostMapping
    @Operation(summary = "관리자 회원가입", description = "관리자 계정 신규 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AdminSignUpRequestBody.class),
                        examples = @ExampleObject(value = "{\"adminId\":\"3\", \"username\":\"admin3\"}")
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
                        examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"Admin already exists\"}")
                    ))
    })
    public ResponseEntity<Admin> signUp(
            @Parameter(description = "관리자 회원가입 요청 데이터", required = true)
            @Valid @RequestBody AdminSignUpRequestBody adminSignUpRequestBody) {
        var admin = adminService.signUp(adminSignUpRequestBody);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "관리자 로그인", description = "관리자 로그인 및 JWT 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(value = "{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzUxOTU3ODkzLCJleHAiOjE3NTE5Njg2OTN9.HXTaubye_6e6IMjR1lmfDuslV9_sFIm--psyn3Nsdr4\"}")
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
                        examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\", \"message\":\"Admin with username admin100 not found\"}")
                    ))
    })
    public ResponseEntity<AdminAuthenticationResponse> authenticate(
            @Parameter(description = "관리자 로그인 요청 데이터", required = true)
            @Valid @RequestBody AdminLoginRequestBody adminLoginRequestBody) {
        var response = adminService.authenticate(adminLoginRequestBody);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-username")
    @Operation(summary = "아이디 중복 확인", description = "아이디 중복 확인")
    public ResponseEntity<Boolean> checkUsernameDuplicate(
            @RequestParam String username) {
        boolean exists = adminService.isUsernameDuplicate(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/users")
    @Operation(summary = "사용자 전체 조회", description = "관리자의 사용자 전체 조회")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
    })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
