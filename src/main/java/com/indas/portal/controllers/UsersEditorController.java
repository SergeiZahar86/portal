package com.indas.portal.controllers;

import com.indas.portal.security.UserDetailsServiceImpl;
import com.indas.portal.security.dto.UserDto;
import com.indas.portal.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_portalAdmin')")
public class UsersEditorController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("")
    public ModelAndView partPage() {
        ModelAndView model = new ModelAndView("usersEditor");
        return model;
    }

    @PutMapping("/save")
    public UserDto saveUser(@RequestBody UserDto userDto){
        return userDetailsService.save(userDto);
    }

    @GetMapping("/all")
    public List<UserDto> getAll(){
        return userDetailsService.getAll();
    }
}
