package com.tarun.ghee.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
