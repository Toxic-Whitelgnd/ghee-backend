package com.tarun.ghee.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private ObjectId id;
    private String name;
    private String description;
    private boolean instock;
    private int offerpercentage;
    @NonNull
    private List<Integer> price;
    @NonNull
    private List<Integer> quantitysize;
    private String ratingStar;
    private int ratings;
    @NonNull
    private int quantity;
}
