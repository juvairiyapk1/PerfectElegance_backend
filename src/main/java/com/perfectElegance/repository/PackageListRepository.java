package com.perfectElegance.repository;

import com.perfectElegance.modal.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageListRepository extends JpaRepository<Packages,Integer> {

}
