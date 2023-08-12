package br.com.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalAmountByClientResponse {

    @JsonProperty("codigo_cliente")
    private int clientId;

    @JsonProperty("quantidade_pedidos")
    private int ordersAmount;
}
