package com.tarun.ghee.services.auth;

import com.tarun.ghee.dto.User.ProfileDTO;
import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.repositary.auth.AuthRepositary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileServices {
    @Autowired
    private AuthRepositary ar;


    public ResponseEntity<?> updateProfile(ProfileDTO profileDTO) {
        try{
            UserModel userModel1 = ar.findByemailaddress(profileDTO.getEmailaddress());
            if(userModel1 != null){
                userModel1.setUsername(profileDTO.getUsername());
                userModel1.setAddress(profileDTO.getAddress());
                userModel1.setDistrict(profileDTO.getDistrict());
                userModel1.setPincode(profileDTO.getPincode());
                userModel1.setState(profileDTO.getState());
                userModel1.setMobilenumber(profileDTO.getMobilenumber());

                ar.save(userModel1);
            }
            log.info("User Information Updated Successflly");
            return ResponseEntity.ok().body(userModel1);
        } catch (Exception e) {
            log.error("Failed to update the user details"+ e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to Update");
        }
    }
}
