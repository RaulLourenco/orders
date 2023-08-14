package br.com.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class OrderItemsDto {

    @JsonProperty("produto")
    private String product;

    @JsonProperty("quantidade")
    private int quantity;

    @JsonProperty("preco")
    private BigDecimal price;
}
