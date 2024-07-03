package com.perfectElegance.service;

import com.perfectElegance.modal.Packages;
import com.perfectElegance.repository.PackageListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageListService {


    @Autowired
    private PackageListRepository packageListRepository;
    public Packages addPackages(Packages packagesData) {
        return packageListRepository.save(packagesData);
    }

    public List<Packages> getPackage() {
        return packageListRepository.findAll();
    }
}
