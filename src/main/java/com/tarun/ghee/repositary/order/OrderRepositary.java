package com.tarun.ghee.repositary.order;

import com.tarun.ghee.entity.order.OrderModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepositary extends MongoRepository<OrderModel, ObjectId> {
    OrderModel findByorderid(String orderid);
    List<OrderModel> findByemailaddress(String email);
}
