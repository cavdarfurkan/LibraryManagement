package com.cavdarfurkan.librarymanagement.auth;

import com.cavdarfurkan.librarymanagement.util.RoleHierarchyUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTOMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(Role::getRole)
                        .collect(Collectors.toSet()),
                user.getMoney());
    }

    public static UserDetailsDTO toDetailsDTO(User user) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(String.valueOf(user.getId()));
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setFirstName(user.getFirstName());
        userDetailsDTO.setLastName(user.getLastName());
        userDetailsDTO.setMoney(user.getMoney());
        userDetailsDTO.setRole(RoleHierarchyUtil.findHighestRole(user.getRoles().stream().toList()).getRole());
        return userDetailsDTO;
    }

    public static User toUser(UserDetailsDTO userDetailsDTO, Role updatedRole) {
        User user = new User();
        user.setId(Long.valueOf(userDetailsDTO.getId()));
        user.setUsername(userDetailsDTO.getUsername());
        user.setFirstName(userDetailsDTO.getFirstName());
        user.setLastName(userDetailsDTO.getLastName());
        user.setMoney(userDetailsDTO.getMoney());
        user.setRoles(Set.of(updatedRole));
        return user;
    }
}
