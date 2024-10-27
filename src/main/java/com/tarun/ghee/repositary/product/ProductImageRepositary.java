package com.tarun.ghee.repositary.product;

import com.tarun.ghee.entity.product.ProductImages;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductImageRepositary extends MongoRepository<ProductImages, ObjectId> {
    ProductImages findByName(String name);
    boolean deleteByname(String name);
}