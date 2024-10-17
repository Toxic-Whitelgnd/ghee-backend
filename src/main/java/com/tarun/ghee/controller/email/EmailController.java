package com.tarun.ghee.controller.email;

import com.tarun.ghee.dto.email.EmailAddDTO;
import com.tarun.ghee.dto.email.EmailSendDTO;
import com.tarun.ghee.entity.email.EmailModel;
import com.tarun.ghee.services.email.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServices es;

    @GetMapping
    public ResponseEntity<?> getAllEmailTemplate(){
       return es.getAllEmailTempalte();
    }

    @PostMapping
    public ResponseEntity<?> addEmailTemplate(@RequestBody EmailAddDTO emailAddDTO){
        return es.addEmailTemplate(emailAddDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateEmailTemplate(@RequestBody EmailModel emailModel){
        return es.updateEmailTemplate(emailModel);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmailTemplate(@RequestBody EmailModel emailModel){
        return es.deleteEmailTemplate(emailModel);
    }

    @PutMapping("/admin/email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailSendDTO emailSendDTO){
        return es.updateAndSendEmail(emailSendDTO);
    }


}
