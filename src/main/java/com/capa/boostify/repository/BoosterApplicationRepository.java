package com.capa.boostify.repository;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.utils.BoosterApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoosterApplicationRepository extends JpaRepository<BoosterApplication, String> {
    Optional<BoosterApplication> findBoosterApplicationByUserAndBoosterApplicationStatus(User user, BoosterApplicationStatus boosterApplicationStatus);

    List<BoosterApplication> findBoosterApplicationsByBoosterApplicationStatus(BoosterApplicationStatus boosterApplicationStatus);
}
