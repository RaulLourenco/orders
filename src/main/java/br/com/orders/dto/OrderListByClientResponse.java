package br.com.orders.dto;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrderItems;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListByClientResponse {

    @JsonProperty("codigo_cliente")
    private int clientId;

    @JsonProperty("pedidos")
    private List<OrderDto> orders;
}
