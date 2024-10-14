package com.tarun.ghee.controller.product;

import com.tarun.ghee.dto.product.ProductDTO;
import com.tarun.ghee.dto.product.ProductUpdateDTO;
import com.tarun.ghee.entity.product.ProductModel;
import com.tarun.ghee.services.products.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class productController {
    @Autowired
    private ProductServices ps;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestPart("data") ProductDTO productDTO,
                                        @RequestPart("images") List<MultipartFile> images)
            throws IOException {
        try{
            return ps.addProduct(productDTO,images);
        } catch (Exception e) {
            log.error("Error",e.getMessage());
        }
        return  ResponseEntity.internalServerError().body("Error in the Saving at ProductController ");
    }

    @GetMapping
    public ResponseEntity<?> getProduct(){
        return ps.getAllProduct();
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestPart("data") ProductUpdateDTO productDTO,
                                           @RequestPart(value = "images",required = false) List<MultipartFile> images)  throws IOException{
        try{
            return ps.updateProduct(productDTO,images);
        } catch (Exception e) {
            log.error("Error",e.getMessage());
        }
        return  ResponseEntity.internalServerError().body("Error in the Updating at ProductController");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestBody ProductDTO productDTO){
        return ps.deleteProduct(productDTO);
    }
}
