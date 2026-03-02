package com.datn.drugstore.controller;

import com.datn.drugstore.response.BaseResponse;
import com.datn.drugstore.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final AwsS3Service awsS3Service;

    @PostMapping
    public ResponseEntity<BaseResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new BaseResponse(400, "File is empty", null));
            }

            String originalFilename = file.getOriginalFilename();
            String extension = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";
            String baseName = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(0, originalFilename.lastIndexOf("."))
                    : (originalFilename != null ? originalFilename : "image");
            String safeName = baseName.replaceAll("[^a-zA-Z0-9._-]", "_").replaceAll("_+", "_");
            String shortId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

            String s3Key = "images/" + shortId + "_" + safeName + extension;
            String url = awsS3Service.uploadFileToS3(file.getInputStream(), file.getSize(), s3Key, file.getContentType());

            return ResponseEntity.ok(new BaseResponse(200, "Upload successful", url));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(new BaseResponse(500, "Upload failed: " + e.getMessage(), null));
        }
    }
}
