package com.tarun.ghee.controller.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @GetMapping
    public String emailtest(){
        return "Email is working with security";
    }
}
