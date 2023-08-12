package br.com.orders.domain;

import br.com.orders.repository.entity.ClientEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private int id;

    private int clientCode;

    private List<OrderItems> items;

    private BigDecimal totalPrice;
}
