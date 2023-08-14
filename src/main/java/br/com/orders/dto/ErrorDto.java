package br.com.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    @JsonProperty("status")
    private int status;

    @JsonProperty("mensagem")
    private String message;
}
