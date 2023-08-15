package br.com.orders.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class OrderItems {

    private String product;

    private int quantity;

    private BigDecimal price;
}
