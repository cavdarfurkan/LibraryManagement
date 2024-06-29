package com.cavdarfurkan.librarymanagement.auth;

import java.util.Set;

public class UserDTO {
    private String username;
    private String password;
    private Set<String> roles;
    private Double money;

    public UserDTO(String username, String password, Set<String> roles, Double money) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
