package com.tarun.ghee.services.setting;

import com.tarun.ghee.constants.EmailConstants;
import com.tarun.ghee.dto.User.AdminAcessDTO;
import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.entity.setting.SettingModel;
import com.tarun.ghee.repositary.auth.AuthRepositary;
import com.tarun.ghee.repositary.setting.SettingRepositary;
import com.tarun.ghee.services.email.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tarun.ghee.utils.Utils.PrepareEmail;

@Service
@Slf4j
public class SettingServices {
    @Autowired
    private SettingRepositary sr;

    @Autowired
    private AuthRepositary ar;

    @Value(("${EMAIL}"))
    private String FromMail;

    @Autowired
    private EmailSender es;


    public ResponseEntity<?> updateSettings(SettingModel settingModel) {
        try {
            ObjectId obj = new ObjectId("6710bbcbafd6174daed1fb8e");

            Optional<SettingModel> settingOptional = sr.findById(obj);

            if (settingOptional.isPresent()) {
                SettingModel existingSetting = settingOptional.get();

                // Update the existing setting with new values from 'settingModel'
                existingSetting.setCustomeremail(settingModel.isCustomeremail());
                existingSetting.setEmailsetting(settingModel.isEmailsetting());
                existingSetting.setIntialbody(settingModel.getIntialbody());
                existingSetting.setIntialsubject(settingModel.getIntialsubject());

                // Save the updated settings
                sr.save(existingSetting);
                log.info("Settings Updated");

                // Return the updated setting object
                return ResponseEntity.ok().body(existingSetting);
            } else {
                // If the setting is not found, return a NOT_FOUND response
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting not found");
            }
        } catch (Exception e) {
            // In case of any errors, return a bad request with the error message
            log.error("Error updating settings: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating settings");
        }
    }

    public SettingModel getSettings(){
        List<SettingModel> ls = sr.findAll();
        return ls.get(0);
    }

    public ResponseEntity<?> grantOrRevokeAdmin(AdminAcessDTO emailAddress) {
        try{
            UserModel user = ar.findByemailaddress(emailAddress.getEmailaddress());

            if (user != null) {
                List<String> roles = user.getRoles();

                if (roles.contains("ADMIN")) {
                    if(emailAddress.isRevoke()){
                        roles.remove("ADMIN");

                        ar.save(user);
                        //Send email
                        SimpleMailMessage sm = PrepareEmail(EmailConstants.adminRevokeSubject,EmailConstants.adminRevokeBody,
                                emailAddress.getEmailaddress(),FromMail);

                        es.SendEmail(sm);
                        return ResponseEntity.ok("Admin Access has revoked");
                    }
                   return ResponseEntity.ok("User already has admin access.");
                } else {
                    roles.add("ADMIN");
                    user.setRoles(roles);

                    ar.save(user);
                    log.info("Admin access granted to user.");
                    //send email
                    SimpleMailMessage sm = PrepareEmail(EmailConstants.adminAccessSubject,EmailConstants.adminAccessBody,
                            emailAddress.getEmailaddress(),FromMail);

                    es.SendEmail(sm);
                    return ResponseEntity.ok("AdminAccess Provided");
                }
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        } catch (Exception e) {
            log.error("Failed to Provide the access");
        }

        return ResponseEntity.internalServerError().body("Failed to provide");

    }
}
