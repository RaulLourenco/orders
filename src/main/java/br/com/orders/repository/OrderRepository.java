package br.com.orders.repository;

import br.com.orders.repository.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Integer>, QueryRepository {
}
