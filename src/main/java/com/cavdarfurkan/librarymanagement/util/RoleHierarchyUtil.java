package com.cavdarfurkan.librarymanagement.util;

import com.cavdarfurkan.librarymanagement.auth.Role;

import java.util.List;

public class RoleHierarchyUtil {
    public static Role findHighestRole(List<Role> roles) {
        Role highestRole = null;
        int highestPriority = Integer.MIN_VALUE;

        for (Role role : roles) {
            int currentPriority = getRolePriority(role);
            if (currentPriority > highestPriority) {
                highestPriority = currentPriority;
                highestRole = role;
            }
        }

        return highestRole;
    }

    private static int getRolePriority(Role role) {
        return switch (role.getRole()) {
            case "ROLE_ADMINISTRATOR" -> 10;
            case "ROLE_LIBRARIAN" -> 9;
            case "ROLE_PUBLISHER" -> 8;
            case "ROLE_READER" -> 7;
            case "ROLE_UNREGISTERED_USER" -> 1;
            default -> 0;
        };
    }
}
