package com.tarun.ghee.controller.payment;

import com.razorpay.RazorpayException;
import com.tarun.ghee.dto.order.OrderDTO;
import com.tarun.ghee.dto.order.PaymentDTO;
import com.tarun.ghee.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class PaymentController {

    @Autowired
    private OrderService os;

    @PostMapping("/createorder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) throws RazorpayException {
        return os.createOrder(orderDTO);
    }

    @PutMapping("/updateorder")
    public ResponseEntity<?> updateOrder(@RequestBody PaymentDTO paymentDTO){
        return os.updateOrder(paymentDTO);
    }

}
