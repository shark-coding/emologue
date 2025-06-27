package com.project.emologue.model.admin;

import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.model.entity.UserEntity;

public record Admin(Long adminId, String username) {

    public static Admin from(AdminEntity adminEntity) {
        return new Admin(
                adminEntity.getAdminId(), adminEntity.getUsername());
    }
}
