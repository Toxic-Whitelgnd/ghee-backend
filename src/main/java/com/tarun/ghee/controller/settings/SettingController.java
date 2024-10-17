package com.tarun.ghee.controller.settings;

import com.tarun.ghee.entity.setting.SettingModel;
import com.tarun.ghee.services.setting.SettingServices;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public SettingModel getSettings(){
        return ss.getSettings();
    }
}
