package com.tarun.ghee.utils;

import com.tarun.ghee.entity.order.OrderModel;
import com.tarun.ghee.enums.EmailContants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public  class Utils {

    @Value(("${FRONTENDURL}"))
    private  String url;

    @Autowired
    private  JavaMailSender mailSender;

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

    public  MimeMessagePreparator  PrepareEmailMessage(String subject,String body,String toEmail,String fromEmail) throws MessagingException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setFrom(fromEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Set `true` for HTML content
        };

        return preparator;
    }

    public static SimpleMailMessage PrepareEmail(String subject,String body,String tomail,String fromEmail){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setSubject(subject);
        sm.setText(body);
        sm.setTo(tomail);
        sm.setFrom(fromEmail);

        return  sm;
    }

    public static String EncodeEmail(String email){
        return URLEncoder.encode(email, StandardCharsets.UTF_8);
    }

    public  String ResetUrl(String encodedEmail ){
        return url+"/#/resetpassword?email=" + encodedEmail;
    }
}
