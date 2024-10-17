package com.tarun.ghee.services.setting;

import com.tarun.ghee.entity.setting.SettingModel;
import com.tarun.ghee.repositary.setting.SettingRepositary;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SettingServices {
    @Autowired
    private SettingRepositary sr;

    public ResponseEntity<?> updateSettings(SettingModel settingModel) {
        try {
            // Assuming 'sr' is your repository interface and it has the 'findById' method
            ObjectId obj = new ObjectId("6710bbcbafd6174daed1fb8e");  // Use dynamic ID instead if needed

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
}
