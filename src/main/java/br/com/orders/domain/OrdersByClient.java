package br.com.orders.domain;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersByClient {

    private int clientId;

    private int orderQuantity;
}
