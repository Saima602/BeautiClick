package com.beuticlick.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.beuticlick.entity.User;
import com.beuticlick.exception.AccessDeniedException;

public class SecurityUtils {

    public static User currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        throw new AccessDeniedException("User", 0L, 0L);
    }

    public static Long currentSalonId() {
        return currentUser().getSalonId();
    }

    public static String currentEmail() {
        return currentUser().getEmail();
    }
}
