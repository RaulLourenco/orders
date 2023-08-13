package br.com.orders.mapper;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrdersByClient;
import br.com.orders.dto.TotalQuantityByClientResponse;
import br.com.orders.dto.TotalValueResponse;
import br.com.orders.repository.entity.OrderEntity;
import br.com.orders.repository.entity.OrdersByClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapFrom(OrderEntity source);

    OrderEntity mapFrom(Order source);

    OrdersByClient mapFrom(OrdersByClientEntity source);

    TotalQuantityByClientResponse mapFrom(OrdersByClient source);

    @Mapping(target = "orderId", source = "id")
    TotalValueResponse mapFromOrder(Order source);
}
