package com.indas.portal.security.dto;

import com.indas.portal.security.entities.Role;

import java.util.Date;
import java.util.List;

public class UserDto {

    public Integer id;

    public String login;

    public String password;

    public String fio;

    public String emplId;

    public Long expiration;

    public List<RoleDto> roles;


}
