package com.devsam.pointofsale.Repository;

import com.devsam.pointofsale.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order, UUID> {
    boolean existsByProductName(String productName);
}
