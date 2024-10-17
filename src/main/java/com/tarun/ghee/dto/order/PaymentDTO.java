package com.tarun.ghee.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String orderid;
    private String paymentid;
    private String paymentsignature;
}
