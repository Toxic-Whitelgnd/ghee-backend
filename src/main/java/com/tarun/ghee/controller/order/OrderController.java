package com.tarun.ghee.controller.order;

import com.tarun.ghee.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService os;

    @GetMapping
    public ResponseEntity<?> getallorders( Authentication auth){
        return os.getAlltheProduct(auth);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getallOrderforAdmin(){
        return os.getAllAdminOrders();
    }


}
