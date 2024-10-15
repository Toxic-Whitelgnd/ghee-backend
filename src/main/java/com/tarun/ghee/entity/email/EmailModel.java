package com.tarun.ghee.entity.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "emailtemplates")
public class EmailModel {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String title;
    @NonNull
    private String subject;
    @NonNull
    private String body;
    @NonNull
    private String status;

    private boolean isdefault;
}
