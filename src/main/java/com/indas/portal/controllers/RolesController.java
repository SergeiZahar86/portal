package com.indas.portal.controllers;

import com.indas.portal.security.dto.RoleDto;
import com.indas.portal.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ROLE_portalAdmin')")
public class RolesController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<RoleDto> getAll(){
        return roleService.getAll();
    }
}
