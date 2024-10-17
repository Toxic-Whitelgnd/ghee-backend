package com.tarun.ghee.services.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarun.ghee.dto.product.ProductDTO;
import com.tarun.ghee.dto.product.ProductUpdateDTO;
import com.tarun.ghee.entity.email.EmailModel;
import com.tarun.ghee.entity.product.ImageData;
import com.tarun.ghee.entity.product.ProductModel;
import com.tarun.ghee.repositary.ProductRepositary;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Service
public class ProductServices {
    @Autowired
    private ProductRepositary pr;

    public ResponseEntity<?> addProductAsync(ProductDTO productDTO, List<MultipartFile> images) throws IOException {
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

    public ResponseEntity<?> updateProduct(ProductUpdateDTO productDTO, List<MultipartFile> images) {
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

                // Retrieve existing images
                List<ImageData> existingImages = new ArrayList<>(productModel.getImages());

                // Prepare to keep track of new images
                List<ImageData> updatedImages = new ArrayList<>();

                // Create a set for existing image filenames for quick lookup
                Set<String> existingImageNames = new HashSet<>();
                if(existingImages != null){
                    for (ImageData imageData : existingImages) {
                        existingImageNames.add(imageData.getFileName());
                    }
                }


                Set<String> addedImageNames = new HashSet<>();

                // Compare and save in the db
                if (images != null && existingImages.size() > images.size()) {
                    // Images have been removed
                    // Identify images to be removed from the existing list
                    for (ImageData existingImage : existingImages) {
                        boolean found = false;
                        for (MultipartFile file : images) {
                            if (existingImage.getFileName().equals(file.getOriginalFilename())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            // Image not found in the incoming images; remove it
                            System.out.println("Removing image: " + existingImage.getFileName());
                            // Optionally remove the image from the database or mark for removal here
                        } else {
                            // If it is found, check if it's already in updatedImages
                            if (!addedImageNames.contains(existingImage.getFileName())) {
                                updatedImages.add(existingImage);
                                addedImageNames.add(existingImage.getFileName()); // Mark this image as added
                            }
                        }
                    }
                } else {
                    // Images have been added or unchanged
                    // Add existing images to updatedImages and ensure no duplicates
                    for (ImageData existingImage : existingImages) {
                        if (!addedImageNames.contains(existingImage.getFileName())) {
                            updatedImages.add(existingImage);
                            addedImageNames.add(existingImage.getFileName()); // Mark this image as added
                        }
                    }

                    // Process new incoming images and ensure no duplicates
                    if(images != null){
                        for (MultipartFile file : images) {
                            if (file != null && !file.isEmpty()) {
                                ImageData newImageData = new ImageData();
                                newImageData.setFileName(file.getOriginalFilename());
                                newImageData.setContentType(file.getContentType());
                                byte[] compressedImage = compressBytes(file.getBytes());
                                newImageData.setData(compressedImage);

                                // Add new image only if it doesn't already exist
                                if (!addedImageNames.contains(newImageData.getFileName())) {
                                    updatedImages.add(newImageData);
                                    addedImageNames.add(newImageData.getFileName()); // Mark this image as added
                                }
                            }
                        }
                    }

                }

                 //Update the product's images list with the newly filtered images
                productModel.setImages(updatedImages);


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


}
