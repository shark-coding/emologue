package com.project.emologue.model.user;

import com.project.emologue.model.entity.UserEntity;

public record User(Long userId, String username, String name, String jobname) {

    public static User from(UserEntity userEntity) {
        return new User(
          userEntity.getUserId(),
          userEntity.getUsername(),
          userEntity.getName(),
          userEntity.getJob().getJobname());
    }
}
