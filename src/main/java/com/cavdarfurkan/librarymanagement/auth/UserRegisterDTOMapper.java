package com.cavdarfurkan.librarymanagement.auth;

public class UserRegisterDTOMapper {
    public static User toEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }
}
