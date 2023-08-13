package br.com.orders.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {

    private String product;

    private int amount;

    private BigDecimal price;
}
