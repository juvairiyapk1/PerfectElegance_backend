package com.perfectElegance.repository;

import com.perfectElegance.modal.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerRepository extends JpaRepository<Partner,Integer> {
}
