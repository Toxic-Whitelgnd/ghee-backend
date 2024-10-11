package com.tarun.ghee.repositary.auth;

import com.tarun.ghee.entity.User.UserModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthRepositary extends MongoRepository<UserModel, ObjectId> {
    UserModel findByusername(String Username);
    UserModel findByemailaddress(String email);
}