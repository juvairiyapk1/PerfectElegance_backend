package com.perfectElegance.controller;

import com.perfectElegance.modal.Packages;
import com.perfectElegance.service.PackageListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class PackageListController {

    @Autowired
    private PackageListService packageListService;

    @PostMapping("/package")
    public ResponseEntity<Map<String,String>>addPackages(@RequestBody Packages packagesData){
        Packages packages = packageListService.addPackages(packagesData);
        Map<String,String> response = new HashMap<>();
        if(packages != null){
            response.put("message","Package successfully added");
            return ResponseEntity.ok(response);
        }else{
             response.put("message","Failed");
             return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/getPackages")
    public List<Packages> getPackages(){
        return packageListService.getPackage();
    }
}
