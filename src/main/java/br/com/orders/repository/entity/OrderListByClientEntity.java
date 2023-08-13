package br.com.orders.repository.entity;

import br.com.orders.domain.OrderItems;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListByClientEntity {

    private int clientId;

    private List<OrderItems> items;
}
