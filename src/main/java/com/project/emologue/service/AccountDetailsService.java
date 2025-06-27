package com.project.emologue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    private UserService userService;
    private AdminService adminService;

    public AccountDetailsService(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return adminService.loadUserByUsername(username);
        }
    }
}
