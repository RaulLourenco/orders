package br.com.orders.repository.entity;

import br.com.orders.domain.OrderItems;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Orders")
public class OrderEntity {

    @Id
    private int id;

    private int clientCode;

    private List<OrderItems> items;

    private BigDecimal totalPrice;
}
