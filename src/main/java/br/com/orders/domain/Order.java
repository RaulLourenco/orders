package br.com.orders.domain;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @NotNull
    @Min(1)
    private int id;

    @NotNull
    @Min(1)
    private int clientCode;

    @NotNull
    @Size(min = 1)
    private List<OrderItems> items;

    private BigDecimal totalPrice;
}
