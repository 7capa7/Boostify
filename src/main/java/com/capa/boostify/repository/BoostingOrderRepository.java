package com.capa.boostify.repository;

import com.capa.boostify.entity.BoostingOrder;
import com.capa.boostify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoostingOrderRepository extends JpaRepository<BoostingOrder, String> {
    List<BoostingOrder> findBoostingOrdersByUser(User user);

    List<BoostingOrder> findBoostingOrdersByBoosterIsNull();
}
