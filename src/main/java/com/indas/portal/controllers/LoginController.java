package com.indas.portal.controllers;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.indas.portal.security.UserDetailsServiceImpl;
import com.indas.portal.service.dto.ChangeUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;;

@RestController
public class LoginController {
//    @Autowired
//    private IICAuthenticationProvider authProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/login")
    public ModelAndView loginPg() {

        ModelAndView model = new ModelAndView("login");
        return model;
    }

    @GetMapping("/changepassword")
    public ModelAndView changepwdPg(HttpSession session) {

        ModelAndView model = new ModelAndView("changepwdPg");
        model.addObject("","");
        return model;
    }

    @PostMapping("/changepassword")
    public boolean changePwd(@RequestBody ChangeUserData userData) throws Exception{
        return userDetailsService.changePassword(userData);
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            if((null!=entry.getValue())&&(entry.getValue().isEmpty())){
//                data.put(entry.getKey(),null);
//            }
//        }
//        authProvider.changePassword(data.get("current_password"), data.get("new_password"), data.get("proxy_card"));
    }

    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }

        return "redirect:/login?logout";
    }
}

