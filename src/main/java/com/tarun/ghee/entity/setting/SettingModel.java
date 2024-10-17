package com.tarun.ghee.entity.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "settings")
public class SettingModel {
    @Id
    private ObjectId id;

    private boolean emailsetting;
    private boolean customeremail;
    private String intialsubject;
    private String intialbody;
}
