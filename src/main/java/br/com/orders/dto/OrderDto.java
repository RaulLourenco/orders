package br.com.orders.dto;

import br.com.orders.domain.OrderItems;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @JsonProperty("codigo_pedido")
    private int id;

    @JsonProperty("codigo_cliente")
    private int clientCode;

    @JsonProperty("itens")
    private List<OrderItemsDto> items;

    @JsonProperty("preco_total")
    private BigDecimal totalPrice;
}
