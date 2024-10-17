package com.tarun.ghee.repositary.email;

import com.tarun.ghee.entity.email.EmailModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailRepositary  extends MongoRepository<EmailModel, ObjectId> {
    EmailModel findBytitle(String title);
    List<EmailModel> findBystatus(String status);
    @Query("SELECT e FROM EmailModel e WHERE e.status = :status AND e.isdefault = true")
    EmailModel findDefaultByStatus(@Param("status") String status);

}
