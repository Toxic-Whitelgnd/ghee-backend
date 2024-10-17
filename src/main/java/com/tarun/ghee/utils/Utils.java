package com.tarun.ghee.utils;

import com.tarun.ghee.entity.order.OrderModel;
import com.tarun.ghee.enums.EmailContants;
import org.springframework.mail.SimpleMailMessage;

public  class Utils {
    public static String ReplaceText(String template, OrderModel order) {
        // Create a mapping of the placeholders to the actual values from the OrderModel
        String[] values = new String[EmailContants.values().length];

        values[EmailContants.CUSTOMERNAME.ordinal()] = order.getUsername(); // Assuming username is the customer name
        values[EmailContants.ORDERID.ordinal()] = order.getOrderid();
        values[EmailContants.RECIPET.ordinal()] = order.getReceipt();
        values[EmailContants.TOTALAMOUNT.ordinal()] = String.valueOf(order.getTotalAmount());
        values[EmailContants.PAYMENTID.ordinal()] = order.getPaymentid();
        values[EmailContants.MOBILENUMBER.ordinal()] = order.getMobilenumber();
        values[EmailContants.ADDRESS.ordinal()] = order.getAddress();
        values[EmailContants.PINCODE.ordinal()] = String.valueOf(order.getPincode());
        values[EmailContants.DISTRICT.ordinal()] = order.getDistrict();
        values[EmailContants.STATE.ordinal()] = order.getState();

        for (EmailContants constant : EmailContants.values()) {
            String placeholder = constant.name();
            String value = values[constant.ordinal()];
            template = template.replace(placeholder, value != null ? value : "");
        }

        return template;
    }

    public static SimpleMailMessage PrepareEmail(String subject,String body,String tomail,String fromEmail){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setSubject(subject);
        sm.setText(body);
        sm.setTo(tomail);
        sm.setFrom(fromEmail);

        return  sm;
    }
}
