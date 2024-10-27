package com.tarun.ghee.repositary.product;

import com.tarun.ghee.entity.product.ProductModel;
import com.tarun.ghee.entity.product.ProductModelTest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductModelTestRepositary extends MongoRepository<ProductModelTest, ObjectId> {
    ProductModelTest findByname(String name);
    ProductModelTest findByid(ObjectId id);
}
