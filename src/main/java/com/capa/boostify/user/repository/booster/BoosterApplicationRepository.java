package com.capa.boostify.user.repository.booster;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoosterApplicationRepository extends JpaRepository<BoosterApplication,String> {
    Optional<BoosterApplication> findBoosterApplicationByUserAndBoosterApplicationStatus(User user, BoosterApplicationStatus boosterApplicationStatus);
}
