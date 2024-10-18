package com.tarun.ghee.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAcessDTO {
    private String emailaddress;
    private boolean revoke;
}
