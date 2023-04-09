package com.capa.boostify.user.repository;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoosterApplicationRepository extends JpaRepository<BoosterApplication,String> {
    Optional<BoosterApplication> findBoosterApplicationByUserAndBoosterApplicationStatus(User user, BoosterApplicationStatus boosterApplicationStatus);
    List<BoosterApplication> findBoosterApplicationsByBoosterApplicationStatus(BoosterApplicationStatus boosterApplicationStatus);
}
