package br.com.orders.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalValueResponse {

    @JsonProperty("codigo_pedido")
    private int orderId;

    @JsonProperty("preco_total")
    private BigDecimal totalPrice;
}
