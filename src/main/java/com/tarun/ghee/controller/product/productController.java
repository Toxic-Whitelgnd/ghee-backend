package com.tarun.ghee.controller.product;

import com.tarun.ghee.dto.product.ProductDTO;
import com.tarun.ghee.dto.product.ProductUpdateDTO;
import com.tarun.ghee.entity.product.ProductImages;
import com.tarun.ghee.services.products.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

//will remove the product controller and related service
@Slf4j
@RestController
@RequestMapping("/product")
public class productController {
    @Autowired
    private ProductServices ps;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO)
            throws IOException {
        try{
            return ps.addProductnew(productDTO);
        } catch (Exception e) {
            log.error("Error",e.getMessage());
        }
        return ResponseEntity.badRequest().body("Error at the Controller");
    }

    @PostMapping("/addimages")
    public ResponseEntity<?> addImgestotheProduct(@RequestBody ProductImages productImages){
        return ps.addImgesTotheProduct(productImages);
    }

    @GetMapping
    public ResponseEntity<?> getProduct(){
        return ps.getAllProduct();
    }
    @GetMapping("/newget")
    public ResponseEntity<?> getProductNew(){
        return ps.getAllProductNew();
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateDTO productDTO)  throws IOException{
        try{
            return ps.updateProduct(productDTO);
        } catch (Exception e) {
            log.error("Error",e.getMessage());
        }
        return  ResponseEntity.internalServerError().body("Error in the Updating at ProductController");
    }
    @PutMapping("/newupdate")
    public ResponseEntity<?> updateProductnew(@RequestBody ProductUpdateDTO productDTO)  throws IOException{
        try{
            return ps.updateProductNew(productDTO);
        } catch (Exception e) {
            log.error("Error",e.getMessage());
        }
        return  ResponseEntity.internalServerError().body("Error in the Updating at ProductController");
    }


    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestBody ProductDTO productDTO){
        return ps.deleteProduct(productDTO);
    }
    @DeleteMapping("/newdelete")
    public ResponseEntity<?> deleteProductNew(@RequestBody ProductDTO productDTO){
        return ps.deleteProductNew(productDTO);
    }

}
