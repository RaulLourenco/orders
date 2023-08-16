package br.com.orders.domain;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    @NotNull
    @NotBlank
    private String product;

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    private BigDecimal price;
}
