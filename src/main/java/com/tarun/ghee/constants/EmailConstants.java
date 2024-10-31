package com.tarun.ghee.constants;

public class EmailConstants {
    public static String intialSubject = "New Order Received - Order #ORDERID";
    public static String intialBody = "Dear GHEE,\n" +
            "\n" +
            "We are pleased to inform you that a new order has been successfully placed by a customer. Below are the details of the order:\n" +
            "\n" +
            "Order Details:\n" +
            "- Order ID: ORDERID\n" +
            "- Customer Name: CUSTOMERNAME\n" +
            "- Contact Number: MOBILENUMBER\n" +
            "- Email Address: EMAILADDRESS\n" +
            "- Total Amount: TOTALAMOUNT\n" +
            "- Payment ID: PAYMENTID\n" +
            "- Order Date: CREATEDAT\n" +
            "\n" +
            "Shipping Information:\n" +
            "- Delivery Address: ADDRESS, PINCODE, DISTRICT, STATE\n" +
            "\n" +
            "Items Ordered:\n" +
            "ITEMS\n" +
            "\n" +
            "Please ensure that the order is processed promptly to meet the delivery timelines. The customer expects delivery within the next 1-2 business days. If there are any issues or delays, kindly inform the customer as soon as possible.\n" +
            "\n" +
            "If you need any additional information regarding this order, feel free to contact the support team.\n" +
            "\n" +
            "Thank you for your continued dedication to providing excellent service to our customers.\n" +
            "\n" ;

    public static String adminAccessSubject = "ADMIN ACCESS GRANTED";
    public static String adminAccessBody = "Hi, \n\nYou have been granted admin access. You can now access admin features in the system. If you have any questions, feel free to contact support.\n\nBest regards,\nGHEE";



    public static String adminRevokeSubject = "ADMIN ACCESS REVOKED";
    public static String adminRevokeBody = "Hi, \n\nYour admin access has been revoked. You no longer have access to the admin features in the system. If you believe this is an error or if you need assistance, please contact support.\n\nBest regards,\nGHEE";


    public static String resetPasswordSubject = "Passsword Reset Request";
    public static String resetPasswordBody ="<html>" +
            "<body>" +
            "<p>Dear User,</p>" +
            "<p>We received a request to reset your password. Click the button below to reset it:</p>" +
            "<a href='" + "RESETURL" + "' style='text-decoration: none;'>" +
            "<button style='padding: 10px 20px; background-color: #EFC850; color: white; border: none; border-radius: 5px; cursor: pointer;'>" +
            "Reset Password" +
            "</button>" +
            "</a>" +
            "<p>If you didn't request this, you can ignore this email.</p>" +
            "<p>Best regards,<br>Ghee</p>" +
            "</body>" +
            "</html>";

}
