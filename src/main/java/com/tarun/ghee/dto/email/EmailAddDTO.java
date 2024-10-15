package com.tarun.ghee.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddDTO {
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
