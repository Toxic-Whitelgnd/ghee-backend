package com.tarun.ghee.repositary.email;

import com.tarun.ghee.entity.email.EmailModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepositary  extends MongoRepository<EmailModel, ObjectId> {
    EmailModel findBytitle(String title);

}
