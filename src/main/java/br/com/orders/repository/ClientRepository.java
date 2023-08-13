package br.com.orders.repository;

import br.com.orders.repository.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientEntity, Integer> {
}
