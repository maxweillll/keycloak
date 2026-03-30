package com.vcreators.pojo;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private String username;
    private Set<String> roles;
}