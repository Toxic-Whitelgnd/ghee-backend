package com.tarun.ghee.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendDTO {
    @NonNull
    private String orderid;
    @NonNull
    private String status;
}
