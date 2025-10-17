package com.devsam.pointofsale.Service;

import com.devsam.pointofsale.Entity.Order;
import com.devsam.pointofsale.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found."));
    }

    @Override
    public void deleteOrderById(UUID id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found."));

        orderRepository.delete(existingOrder);
    }

    @Override
    public Order updateOrder(UUID id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found."));

        // Update fields
        existingOrder.setProductName(updatedOrder.getProductName());
        existingOrder.setAmount(updatedOrder.getAmount());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setStatus(updatedOrder.getStatus());
        return orderRepository.save(existingOrder);
    }
}
