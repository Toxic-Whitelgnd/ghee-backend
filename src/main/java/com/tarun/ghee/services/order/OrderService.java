package com.tarun.ghee.services.order;

import com.razorpay.Item;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tarun.ghee.constants.EmailConstants;
import com.tarun.ghee.dto.order.OrderDTO;
import com.tarun.ghee.dto.order.PaymentDTO;
import com.tarun.ghee.entity.order.ItemModel;
import com.tarun.ghee.entity.order.OrderModel;
import com.tarun.ghee.entity.setting.SettingModel;
import com.tarun.ghee.enums.OrderStatus;
import com.tarun.ghee.repositary.order.OrderRepositary;
import com.tarun.ghee.services.email.EmailSender;
import com.tarun.ghee.services.setting.SettingServices;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tarun.ghee.utils.Utils.PrepareEmail;
import static com.tarun.ghee.utils.Utils.ReplaceText;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepositary or;

    @Autowired
    private SettingServices ss;

    @Value("${RAZOR_KEY}")
    private String RAZORKEY;
    @Value("${RAZOR_KEY_SECRET}")
    private String RAZORKEYSECRET;

    @Value(("${EMAIL}"))
    private String FromMail;

    @Autowired
    private EmailSender es;



    public ResponseEntity<?> createOrder(OrderDTO orderDTO) throws RazorpayException {
        try{
            RazorpayClient razorpay = new RazorpayClient(RAZORKEY, RAZORKEYSECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",orderDTO.getTotalAmount()*100);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt", orderDTO.getReceipt());
            JSONObject notes = new JSONObject();
            notes.put("notes_key_1", "Tea, Earl Grey, Hot");
//            orderRequest.put("notes",orderDTO.getNote());


            Order order = razorpay.orders.create(orderRequest);
            String id = order.get("id");

            OrderModel oe = new OrderModel();
            oe.setOrderid(id);
            oe.setTotalAmount(orderDTO.getTotalAmount());
            List<ItemModel> ls = orderDTO.getItems();
            for(ItemModel i: ls){
                i.setStatus(OrderStatus.PREPARATION.toString());
            }
            oe.setItems(ls);
            oe.setReceipt(orderDTO.getReceipt());
            oe.setNote(orderDTO.getNote());
            oe.setCreatedAt(orderDTO.getCreatedAt());

            oe.setUsername(orderDTO.getUsername());
            oe.setEmailaddress(orderDTO.getEmailaddress());
            oe.setAddress(orderDTO.getAddress());
            oe.setDistrict(orderDTO.getDistrict());
            oe.setPincode(orderDTO.getPincode());
            oe.setState(orderDTO.getState());
            oe.setMobilenumber(orderDTO.getMobilenumber());

            oe.setStatus(OrderStatus.PREPARATION.toString());

            or.save(oe);
            log.info("Created the order"+id+"SAVED THE ORDER TO DB");
            return ResponseEntity.ok().body(oe);

        } catch (Exception e) {
            log.error("ERROR CREATING THE ORDER"+e.getMessage());
        }

        return ResponseEntity.internalServerError().body("Failed to create a order");
    }

    public ResponseEntity<?> updateOrder(PaymentDTO paymentDTO) {
        try{
            OrderModel oe = or.findByorderid(paymentDTO.getOrderid());
            oe.setPaymentid(paymentDTO.getPaymentid());
            oe.setPaymentsignature(paymentDTO.getPaymentsignature());
            or.save(oe);
            log.info("Payment details Updated");
            SettingModel s = ss.getSettings();

            if( s.isCustomeremail()){
                String replacedSubject = ReplaceText(s.getIntialsubject(),oe);
                String repalcedBody = ReplaceText(s.getIntialbody(), oe);

                SimpleMailMessage sm = PrepareEmail(replacedSubject,repalcedBody,
                        oe.getEmailaddress(),FromMail);

                es.SendEmail(sm);

                log.info("Email delivered for the Client");

            }
            if(s.isEmailsetting()){
                String replacedSubject = ReplaceText(EmailConstants.intialSubject,oe);
                String repalcedBody = ReplaceText(EmailConstants.intialBody, oe);
                SimpleMailMessage sm = PrepareEmail(replacedSubject,repalcedBody,
                        oe.getEmailaddress(),FromMail);

                es.SendEmail(sm);

                log.info("Email delivered for Bussiness Contact");

            }

            return ResponseEntity.ok().body("Updated and Email Delivered");
        } catch (Exception e) {
            log.error("Updating Failed for the Payment");
        }
        return ResponseEntity.badRequest().body("Failed to update");
    }

    public ResponseEntity<?> getAlltheProduct( Authentication auth) {
        //by using the jwt tokens we will get the email
       try{
           String  email = auth.getName();
           List<OrderModel> oe = or.findByemailaddress(email);
           log.info("Orders Have been fetched");
           return ResponseEntity.ok().body(oe);
       } catch (Exception e) {

           log.error("Failed to Fetch the Orders");
           return ResponseEntity.badRequest().body("Failed to fetch the orders for the User");
       }



    }

    public ResponseEntity<?> getAllAdminOrders() {
        try{
            List<OrderModel> ls = or.findAll();
            log.info("Fetched the admin Orders");
            return ResponseEntity.ok().body(ls);
        } catch (Exception e) {
            log.error("Failed to fetch the orders");
            return ResponseEntity.internalServerError().body("Failed to fetch the orders");
        }

    }
}

//public ResponseEntity<?> testwithjwt(Authentication auth){
//    String username = auth.getName();
//    var res = new HashMap<String, Object>();
//    res.put("username", username);
//    var us = pr.findByusername(username);
//    res.put("pgOwnerDetails",us);
//    return ResponseEntity.ok(res);
//}