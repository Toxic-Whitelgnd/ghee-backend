package com.tarun.ghee.controller;

import com.tarun.ghee.services.products.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private ProductServices ps;

    @GetMapping("/product")
    public ResponseEntity<?> getProduct(){
        return ps.getAllProduct();
    }
}
