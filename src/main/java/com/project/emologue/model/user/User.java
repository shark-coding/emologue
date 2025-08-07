package com.project.emologue.model.user;

import com.project.emologue.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record User(
        @Schema(description = "사용자 PK", example = "1")
        Long userId,
        @Schema(description = "사용자 아이디", example = "sanga")
        String username,
        @Schema(description = "사용자 이름", example = "Kim Sanga")
        String name,
        @Schema(description = "사용자 직업", example = "backend")
        String jobname) {

    public static User from(UserEntity userEntity) {
        return new User(
          userEntity.getUserId(),
          userEntity.getUsername(),
          userEntity.getName(),
          userEntity.getJob().getJobname());
    }
}
