package com.tarun.ghee.dto.order;

import com.tarun.ghee.entity.order.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderDTO {

        private String username;
        private String mobilenumber;
        private String emailaddress;
        private String address;
        private int pincode;
        private String state;
        private String district;

        private String paymentmode;

        private List<ItemModel> items;
        private double totalAmount;
        private String receipt;
        private String note;
        private Date createdAt;
    }
