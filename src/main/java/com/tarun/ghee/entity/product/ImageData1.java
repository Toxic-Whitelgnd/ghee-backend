package com.tarun.ghee.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageData1 {
    private String fileName;
    private String contentType;
    private String data;
    private String fileId;
}
