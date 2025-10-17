package com.devsam.pointofsale.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private String productName;
    private BigDecimal amount;
    private Integer quantity;
}
