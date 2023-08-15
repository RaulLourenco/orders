package br.com.orders.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "Clients")
public class ClientEntity {

    @Id
    private int id;
}