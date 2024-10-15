package com.tarun.ghee.repositary;

import com.tarun.ghee.entity.product.ProductModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepositary extends MongoRepository<ProductModel, ObjectId> {
    ProductModel findByname(String name);
    ProductModel findByid(ObjectId id);
}
