package com.tarun.ghee.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProcessDTO {

    private String name;
    private String  email;
    private String  mobilenumber;
    private String  address;
    private int  pincode;
    private String  state;
    private String  district;
}
