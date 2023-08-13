package br.com.orders.repository.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersByClientEntity {

    private int clientId;

    private int orderQuantity;
}
