package com.perfectElegance.repository;

import com.perfectElegance.modal.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    @EntityGraph(attributePaths = {"user", "partner"})
    Profile findByUserId(Integer userId);

}
