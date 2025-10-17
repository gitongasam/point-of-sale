package com.devsam.pointofsale.Service;

import com.devsam.pointofsale.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderService {

    //    add an order
    Order createOrder(Order order);

    //    get all orders
    Page<Order> getAllOrders(Pageable pageable);

    //    get order by id

    Order getOrderById(UUID id);

    Order updateOrder(UUID id, Order updatedOrder);


    // Delete order by ID

    void deleteOrderById(UUID id);}
//update order by id

