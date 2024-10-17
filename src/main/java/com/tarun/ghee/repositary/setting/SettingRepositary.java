package com.tarun.ghee.repositary.setting;

import com.tarun.ghee.entity.setting.SettingModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingRepositary extends MongoRepository<SettingModel, ObjectId> {

}
