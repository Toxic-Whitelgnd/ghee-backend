package com.tarun.ghee.services.auth;

import com.tarun.ghee.dto.User.UserDTO;
import com.tarun.ghee.dto.User.UserLoginDTO;
import com.tarun.ghee.dto.User.UserProcessDTO;
import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.repositary.auth.AuthRepositary;
import com.tarun.ghee.services.JwtConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AuthServices {


    @Autowired
    private AuthRepositary ar;

    @Autowired
    private JwtConfigService jwt;

    public ResponseEntity<?> registerUser(UserDTO userDTO){
        var bcryptEncoder = new BCryptPasswordEncoder();

        UserModel us = new UserModel();
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        us.setUsername(userDTO.getUsername());
        us.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
        us.setEmailaddress(userDTO.getEmailaddress());
        us.setMobilenumber(userDTO.getMobilenumber());
        us.setRoles(roles);

        var otheruser = ar.findByemailaddress(userDTO.getEmailaddress());
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be null or empty");
        }

        if (otheruser != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        try{

            ar.save(us);
            log.info("USER CREATED");

            String jwtToken = jwt.createJWTtokens(us);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", us);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error("Error while creating USER {}", e.getMessage());
        }



        return new ResponseEntity<>(userDTO, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> loginUser(UserLoginDTO userLoginDTO){
        UserModel us = ar.findByemailaddress(userLoginDTO.getEmail());
        String jwtToken = jwt.createJWTtokens(us);
        var response = new HashMap<String, Object>();
        response.put("token", jwtToken);
        response.put("user", us);
        log.info("Login Successfull");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> registerOnProcessUser(UserProcessDTO userDTO){
        var bcryptEncoder = new BCryptPasswordEncoder();

        UserModel us = new UserModel();
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        us.setUsername(userDTO.getName());
        us.setPassword(bcryptEncoder.encode(userDTO.getMobilenumber()));
        us.setEmailaddress(userDTO.getEmail());
        us.setMobilenumber(userDTO.getMobilenumber());
        us.setAddress(userDTO.getAddress());
        us.setState(userDTO.getState());
        us.setPincode(userDTO.getPincode());
        us.setDistrict(userDTO.getDistrict());

        us.setRoles(roles);

        var otheruser = ar.findByemailaddress(userDTO.getEmail());
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be null or empty");
        }

        if (otheruser != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        try{

            ar.save(us);
            log.info("USER CREATED");

            String jwtToken = jwt.createJWTtokens(us);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", us);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error("Error while creating USER {}", e.getMessage());
        }



        return new ResponseEntity<>(userDTO, HttpStatus.BAD_REQUEST);
    }
}
