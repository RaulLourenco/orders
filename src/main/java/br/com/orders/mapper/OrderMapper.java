package br.com.orders.mapper;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrdersByClient;
import br.com.orders.repository.entity.OrderEntity;
import br.com.orders.repository.entity.OrdersByClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapFrom(OrderEntity orderEntity);

    OrderEntity mapFrom(Order order);

    OrdersByClient mapFrom(OrdersByClientEntity entity);
}
