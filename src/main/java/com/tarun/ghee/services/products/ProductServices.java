package com.tarun.ghee.services.products;

import com.tarun.ghee.dto.product.ProductDTO;
import com.tarun.ghee.dto.product.ProductUpdateDTO;
import com.tarun.ghee.entity.product.*;
import com.tarun.ghee.repositary.product.ProductImageRepositary;
import com.tarun.ghee.repositary.product.ProductModelTestRepositary;
import com.tarun.ghee.repositary.product.ProductRepositary;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Service
public class ProductServices {
    @Autowired
    private ProductRepositary pr;

    @Autowired
    private ProductImageRepositary pir;

    @Autowired
    private ProductModelTestRepositary pt;

    @Autowired
    private GridFsTemplate gridFsTemplate;
    //TODO: refactor the products page


    public ResponseEntity<?> addProductAsync(ProductDTO productDTO,List<MultipartFile> images) throws IOException {
        List<ImageData> imageDataList = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        try {
            // Process each image
            for (MultipartFile file : images) {
                ImageData imageData = new ImageData();
                imageData.setFileName(file.getOriginalFilename());
                imageData.setContentType(file.getContentType());
                byte[] compressedImage = compressBytes(file.getBytes());
                imageData.setData(compressedImage);
                imageDataList.add(imageData);
            }

            productModel.setName(productDTO.getName());
            productModel.setDescription(productDTO.getDescription());
            productModel.setPrice(productDTO.getPrice());
            productModel.setQuantitysize(productDTO.getQuantitysize());
            productModel.setQuantity(productDTO.getQuantity());
            productModel.setRatings(productDTO.getRatings());
            productModel.setOfferpercentage(productDTO.getOfferpercentage());
            productModel.setRatingStar(productDTO.getRatingStar());
            productModel.setInstock(productDTO.isInstock());

            // Add images to the product object
            productModel.setImages(imageDataList);

            // Save product
            pr.save(productModel);
            log.info("Product added successfully");

            return ResponseEntity.ok().body(productModel);

        } catch (Exception e) {
            log.error("Error occurred while adding the product", e);
            return ResponseEntity.badRequest().body("Check the server");
        }
    }

    public ResponseEntity<?> getAllProduct() {
        List<ProductModel> productModels = pr.findAll();
        if (productModels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Decompress image data for each product
        for (ProductModel productModel : productModels) {
            List<ImageData> images = productModel.getImages();

            if (images != null) {
                for (ImageData imageData : images) {
                    try {
                        // Decompress the image data
                        byte[] decompressedData = decompressBytes(imageData.getData());
                        imageData.setData(decompressedData); // Update the imageData with decompressed data
                    } catch (IOException e) {
                        log.error("Error decompressing image data for product: " + productModel.getId(), e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error decompressing image data");
                    }
                }
            }
        }

        log.info("Product have been fetched");
        return ResponseEntity.ok(productModels);

    }

    public ResponseEntity<?> updateProduct(ProductUpdateDTO productDTO) {
        ProductModel productModel = pr.findByname(productDTO.getName());

        List<ImageData> imageDataList;
        try{
            if(productModel != null){
                productModel.setName(productDTO.getName());
                productModel.setDescription(productDTO.getDescription());
                productModel.setPrice(productDTO.getPrice());
                productModel.setQuantitysize(productDTO.getQuantitysize());
                productModel.setQuantity(productDTO.getQuantity());
                productModel.setRatings(productDTO.getRatings());
                productModel.setOfferpercentage(productDTO.getOfferpercentage());
                productModel.setRatingStar(productDTO.getRatingStar());
                productModel.setInstock(productDTO.isInstock());



                // Save updated product
                pr.save(productModel);

                log.info("Product Updated Successfully");
                return ResponseEntity.ok().body(productModel);
            }
        } catch (Exception e) {
            log.error("Error occured while updating the product",e.getMessage());
        }
        return ResponseEntity.badRequest().body("Check the server");
    }

    public ResponseEntity<?> deleteProduct(ProductDTO productDTO){
        ProductModel productModel = pr.findByname(productDTO.getName());
        try{
            log.info("Email Deleted Successfully"+productModel.getName());
            pr.deleteById(productModel.getId());
            return ResponseEntity.ok().body("Product Deleted Successfully");
        } catch (Exception e) {
            log.error("Failed to delete"+e.getMessage());
            return ResponseEntity.badRequest().body("Failed to delete Product");
        }
    }

    private byte[] compressBytes(byte[] data) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(data.length);
        try (GZIPOutputStream gzip = new GZIPOutputStream(byteStream)) {
            gzip.write(data);
        }
        return byteStream.toByteArray();
    }

    private byte[] decompressBytes(byte[] data) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(data))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                byteStream.write(buffer, 0, len);
            }
        }
        return byteStream.toByteArray();
    }


    public ResponseEntity<?> addImgesTotheProduct(ProductImages productImages) {

        try{
            for (ImageData1 image : productImages.getImages()) {
                // Convert base64 data to InputStream
                String base64Data = image.getData().replaceFirst("^data:image/\\w+;base64,", "");
                byte[] decodedBytes = Base64.getDecoder().decode(image.getData());
                InputStream dataStream = new ByteArrayInputStream(decodedBytes);

                // Store the image in GridFS
                ObjectId fileId = gridFsTemplate.store(dataStream, image.getContentType(), "image/jpeg");

                // Save the reference to the file ID instead of the entire image data
                image.setFileId(fileId.toString());
                image.setData(null);  // Clear the data field to avoid large storage in MongoDB document
            }
            pir.save(productImages);
            log.info("Images added successfully");
            return ResponseEntity.ok().body("Images Added");
        } catch (Exception e) {
            log.error("Failed to add the Images",e.getMessage());
            return ResponseEntity.badRequest().body("Cannot uplod the imges ");
        }

    }

    public ResponseEntity<?> addProductnew(ProductDTO productDTO){
        ProductModelTest productModel = new ProductModelTest();

        try{
            if(productModel != null){
                productModel.setName(productDTO.getName());
                productModel.setDescription(productDTO.getDescription());
                productModel.setPrice(productDTO.getPrice());
                productModel.setQuantitysize(productDTO.getQuantitysize());
                productModel.setQuantity(productDTO.getQuantity());
                productModel.setRatings(productDTO.getRatings());
                productModel.setOfferpercentage(productDTO.getOfferpercentage());
                productModel.setRatingStar(productDTO.getRatingStar());
                productModel.setInstock(productDTO.isInstock());



                // Save updated product
                pt.save(productModel);

                log.info("Product Updated Successfully");
                return ResponseEntity.ok().body(productModel);
            }
        } catch (Exception e) {
            log.error("Error occured while updating the product",e.getMessage());
        }
        return ResponseEntity.badRequest().body("Check the server");
    }

    public ResponseEntity<?> updateProductNew(ProductUpdateDTO productDTO) {
        ProductModelTest productModel = pt.findByname(productDTO.getName());
        if(productModel != null){
            productModel.setName(productDTO.getName());
            productModel.setDescription(productDTO.getDescription());
            productModel.setPrice(productDTO.getPrice());
            productModel.setQuantitysize(productDTO.getQuantitysize());
            productModel.setQuantity(productDTO.getQuantity());
            productModel.setRatings(productDTO.getRatings());
            productModel.setOfferpercentage(productDTO.getOfferpercentage());
            productModel.setRatingStar(productDTO.getRatingStar());
            productModel.setInstock(productDTO.isInstock());

            pt.save(productModel);
            return ResponseEntity.ok().body("Updated");
        }
        return ResponseEntity.badRequest().body("Update failed");
    }

    public ResponseEntity<?> getAllProductNew() {
        List<ProductModelTest> productModels = pt.findAll();
        if (productModels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ProductModelImage> pmi = new ArrayList<>();
        for(ProductModelTest productDTO:productModels){
            ProductImages pi = pir.findByName(productDTO.getName());
            ProductModelImage productModel = new ProductModelImage();
            if(pi != null){
                productModel.setImages(pi.getImages());
            }

            productModel.setName(productDTO.getName());
            productModel.setDescription(productDTO.getDescription());
            productModel.setPrice(productDTO.getPrice());
            productModel.setQuantitysize(productDTO.getQuantitysize());
            productModel.setQuantity(productDTO.getQuantity());
            productModel.setRatings(productDTO.getRatings());
            productModel.setOfferpercentage(productDTO.getOfferpercentage());
            productModel.setRatingStar(productDTO.getRatingStar());
            productModel.setInstock(productDTO.isInstock());

            pmi.add(productModel);
        }


        log.info("Product have been fetched");
        return ResponseEntity.ok(pmi);

    }

    public ResponseEntity<?> deleteProductNew(ProductDTO productDTO){
        ProductModelTest productModel = pt.findByname(productDTO.getName());
        try{
            log.info("Email Deleted Successfully"+productModel.getName());
            pt.deleteById(productModel.getId());

            return ResponseEntity.ok().body("Product Deleted Successfully");
        } catch (Exception e) {
            log.error("Failed to delete"+e.getMessage());
            return ResponseEntity.badRequest().body("Failed to delete Product");
        }
    }

}
