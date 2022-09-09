package com.spring_batch.utils;

import com.spring_batch.enums.UserRole;

import java.util.HashSet;
import java.util.Set;

public class AppUtils {

    public static Set<UserRole> convertStringRolesSetToEnumSet(Set<String> roles) {
        Set<UserRole> userRoleSet = new HashSet<>();
        for (String role : roles) {
            userRoleSet.add(Enum.valueOf(UserRole.class, role));
        }
        return userRoleSet;
    }
}
