package com.tarun.ghee.controller.settings;

import com.tarun.ghee.dto.User.AdminAcessDTO;
import com.tarun.ghee.entity.setting.SettingModel;
import com.tarun.ghee.services.email.EmailSender;
import com.tarun.ghee.services.setting.SettingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingController {

    @Autowired
    private SettingServices ss;

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody  SettingModel settingModel){
            return ss.updateSettings(settingModel);
    }

    @PostMapping("/adminaccess")
    public ResponseEntity<?> accessOrRevoke(@RequestBody AdminAcessDTO emailAddress){
        return ss.grantOrRevokeAdmin(emailAddress);
    }

    @GetMapping
    public SettingModel getSettings(){
        return ss.getSettings();
    }
}
