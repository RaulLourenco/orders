package br.com.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalQuantityByClientResponse {

    @JsonProperty("codigo_cliente")
    private int clientId;

    @JsonProperty("quantidade_pedidos")
    private int orderQuantity;
}
