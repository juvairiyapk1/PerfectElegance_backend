//package com.perfectElegance.controller;
//
//import com.perfectElegance.service.FileUpload;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class FileUploadController {
//
//    private final FileUpload fileUpload;
//
//    @PostMapping("/upload")
//    public ResponseEntity<Map> uploadFile(@RequestParam("image")MultipartFile multipartFile) {
//
//            String folder = "Images";
//            Map data = fileUpload.upload(multipartFile,folder);
//            return ResponseEntity.ok(data);
//    }
//}
