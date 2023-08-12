package br.com.orders.repository;

import br.com.orders.repository.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryRepository {
    List<OrderEntity> findAllOrdersByClient(Integer clientId, Pageable pageable);
}
