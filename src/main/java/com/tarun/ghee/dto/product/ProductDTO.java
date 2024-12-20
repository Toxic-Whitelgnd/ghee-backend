package com.tarun.ghee.dto.product;

import com.tarun.ghee.entity.product.ImageData;
import com.tarun.ghee.entity.product.ProductImages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
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
