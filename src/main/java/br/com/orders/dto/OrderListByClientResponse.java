package br.com.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
public class OrderListByClientResponse {

    @JsonProperty("codigo_cliente")
    private int clientId;

    @JsonProperty("pedidos")
    private List<OrderDto> orders;
}
