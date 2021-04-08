package com.indas.portal.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
//     внедряем значение из application.properties
//    @Value("${welcome.message:test}")
//    private String message = "Hello World";

    @GetMapping("/")
    public ModelAndView loginPg() {
        ModelAndView model = new ModelAndView("tablePart");
        //model.addObject("message", this.message);
        return model;
    }
}
