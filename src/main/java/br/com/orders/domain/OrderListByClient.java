package br.com.orders.domain;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListByClient {

    private int clientId;

    private List<OrderItems> items;
}
