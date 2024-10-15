package com.tarun.ghee.services.email;

import com.tarun.ghee.dto.email.EmailAddDTO;
import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.entity.email.EmailModel;
import com.tarun.ghee.repositary.email.EmailRepositary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmailServices {

    @Autowired
    private EmailRepositary er;
    public ResponseEntity<?> addEmailTemplate (EmailAddDTO emailAddDTO){
        //check for the same title,
        EmailModel em = er.findBytitle(emailAddDTO.getTitle());

        EmailModel newEm = new EmailModel();

        newEm.setTitle(emailAddDTO.getTitle());
        newEm.setBody(emailAddDTO.getBody());
        newEm.setSubject(emailAddDTO.getSubject());
        newEm.setStatus(emailAddDTO.getStatus());
        newEm.setIsdefault(emailAddDTO.isIsdefault());

        if(em != null){
            //change tht title
            log.error("Email title already exists");
            return ResponseEntity.badRequest().body("Email title already exists");
        }
        else{
            try{
                er.save(newEm);
                log.info("Email added Successfully");
                return ResponseEntity.ok().body("Email added Successfully");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public ResponseEntity<?> getAllEmailTempalte(){
        List<EmailModel> emailTemplates = er.findAll();

        if (emailTemplates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        log.info("Email Templates have been fetched");
        return ResponseEntity.ok(emailTemplates);
    }

    public ResponseEntity<?> updateEmailTemplate(EmailModel emailModel){
        EmailModel existingEmail = er.findBytitle(emailModel.getTitle());
        try{
            if (existingEmail != null) {
                existingEmail.setSubject(emailModel.getSubject());
                existingEmail.setBody(emailModel.getBody());
                existingEmail.setStatus(emailModel.getStatus());
                existingEmail.setIsdefault(emailModel.isIsdefault());

                // Save the updated EmailModel
                er.save(existingEmail);
                log.info("Email Updated Successfully for"+existingEmail.getTitle());

                return ResponseEntity.ok(existingEmail);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Email template with title " + emailModel.getTitle() + " not found.");
            }


        } catch (Exception e)  {

            log.error("Email updation failed"+e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Email template with ID " + emailModel.getId() + " not found.");
        }
    }

    public ResponseEntity<?> deleteEmailTemplate(EmailModel emailModel){
        EmailModel existingEmail = er.findBytitle(emailModel.getTitle());
        try{
            log.info("Email Deleted Successfully"+existingEmail.getTitle());
            er.deleteById(existingEmail.getId());
            return ResponseEntity.ok().body("Email Template Deleted");
        } catch (Exception e) {
            log.error("Failed to delete"+e.getMessage());
            return ResponseEntity.badRequest().body("Email Tempalte Failed to delete");
        }
    }
}
