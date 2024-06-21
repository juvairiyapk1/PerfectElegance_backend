package com.perfectElegance.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload{

    private final Cloudinary cloudinary;


    @Override
    public Map upload(MultipartFile file, String folder) {
        try {
            Map<String, Object> options = ObjectUtils.asMap("folder", folder);
            Map<String, Object> data = cloudinary.uploader().upload(file.getBytes(), options);
            String imageUrl = (String) data.get("secure_url");
            System.out.println("Image Url"+imageUrl);
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Image uploading failed !");
        }
    }
}
