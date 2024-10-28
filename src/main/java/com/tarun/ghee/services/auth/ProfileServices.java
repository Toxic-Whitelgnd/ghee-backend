package com.tarun.ghee.services.auth;

import com.tarun.ghee.dto.User.ProfileDTO;
import com.tarun.ghee.dto.User.ResetPasswordDTO;
import com.tarun.ghee.dto.User.UserLoginDTO;
import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.repositary.auth.AuthRepositary;
import com.tarun.ghee.services.JwtConfigService;
import com.tarun.ghee.services.email.EmailSender;
import com.tarun.ghee.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.tarun.ghee.constants.EmailConstants.resetPasswordBody;
import static com.tarun.ghee.constants.EmailConstants.resetPasswordSubject;
import static com.tarun.ghee.utils.Utils.*;

@Slf4j
@Service
public class ProfileServices {
    @Autowired
    private AuthRepositary ar;

    @Value(("${EMAIL}"))
    private String FromMail;

    @Autowired
    private EmailSender es;

    @Autowired
    private Utils ut;

    @Autowired
    private JwtConfigService jwt;

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

    public ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        try{
            //check for the user existence
            UserModel us = ar.findByemailaddress(resetPasswordDTO.getEmail());
            if(us != null){
                //prepare a email subject, body
                String encodeemail = EncodeEmail(us.getEmailaddress());
                String reseturl = ut.ResetUrl(encodeemail);
                String subject = resetPasswordSubject;
                String body = resetPasswordBody;

                //Replace with the reset url
                body = body.replace("RESETURL",reseturl);

                MimeMessagePreparator sm = ut.PrepareEmailMessage(
                        subject, body , us.getEmailaddress() , FromMail
                );

                es.SendEmailMessage(sm);

                return ResponseEntity.ok().body("Email has been sent");
            }
            return ResponseEntity.badRequest().body("No user Found");
        } catch (Exception e) {
            log.error("Failed to send Email",e.getMessage());
        }
        return ResponseEntity.internalServerError().body("Failed to send");
    }

    public ResponseEntity<?> resetNewPassword(UserLoginDTO userLoginDTO) {
        try{
            UserModel us = ar.findByemailaddress(userLoginDTO.getEmail());
            if(us != null){
                var bcryptEncoder = new BCryptPasswordEncoder();
                us.setPassword(bcryptEncoder.encode(userLoginDTO.getPassword()));

                ar.save(us);
                String jwtToken = jwt.createJWTtokens(us);

                var response = new HashMap<String, Object>();
                response.put("token", jwtToken);
                response.put("user", us);

                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            log.error("Failed to Update the password",e.getMessage());
        }
        return ResponseEntity.badRequest().body("Failed to Reset the password");
    }
}
