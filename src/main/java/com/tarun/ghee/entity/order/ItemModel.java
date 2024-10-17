package com.tarun.ghee.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {

//    @Id
//    private ObjectId id;
    private String name;
    private List<Double> price;
    private int quantity;
    private String description;
    private List<Integer> quantitySize;
    private double offerPrice;
    private boolean inStock;
    private int itemQty;
    private double finalPrice;
    private String status;
}
