package com.perfectElegance.controller;

import com.perfectElegance.modal.Packages;
import com.perfectElegance.service.PackageListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PackageController {

    @Autowired
    private PackageListService packageListService;

    @GetMapping("/getPackages")
    public List<Packages> getPackages(){
        return packageListService.getPackage();
    }
}
