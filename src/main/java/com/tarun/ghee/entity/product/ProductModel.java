package com.tarun.ghee.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductModel {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
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
    private List<ImageData> images;
    @NonNull
    private int quantity;
}

