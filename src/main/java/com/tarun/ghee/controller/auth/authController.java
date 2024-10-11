package com.tarun.ghee.controller.auth;

import com.tarun.ghee.dto.User.UserDTO;
import com.tarun.ghee.dto.User.UserLoginDTO;
import com.tarun.ghee.services.auth.AuthServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
public class authController {
    @Autowired
    private AuthServices as;

    @Autowired
    private AuthenticationManager am;


    @GetMapping
    public ResponseEntity<?> TestApi(){
        return ResponseEntity.ok("Working implement a Post");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        return as.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPGuser(@RequestBody UserLoginDTO ownerLoginDTO){
        try{
//            am.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            ownerLoginDTO.getEmail(),
//                            ownerLoginDTO.getPassword()
//                    )
//            );

            return as.loginUser(ownerLoginDTO);

        } catch (Exception e) {
            log.error("Invalid User {0}",e.getMessage());
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

}
