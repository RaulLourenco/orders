package br.com.orders.domain;

import lombok.*;

@Data
@Getter
@Setter
public class Client {

    private int id;

    private int orderQuantity;
}
