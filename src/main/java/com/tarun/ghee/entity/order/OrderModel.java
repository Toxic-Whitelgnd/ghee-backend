package com.tarun.ghee.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderModel {
    @Id
    private ObjectId id;
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

    private String orderid;
    private String paymentid;
    private String paymentsignature;

    private String paymentverification;

    private String status;
}
