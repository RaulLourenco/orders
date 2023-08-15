package br.com.orders.mapper;

import br.com.orders.domain.Order;
import br.com.orders.dto.OrderDto;
import br.com.orders.dto.TotalValueResponse;
import br.com.orders.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapFrom(OrderEntity source);

    OrderEntity mapFrom(Order source);

    @Mapping(target = "orderId", source = "id")
    TotalValueResponse mapFromOrder(Order source);

    List<OrderDto> mapToResponse(List<Order> source);
}
