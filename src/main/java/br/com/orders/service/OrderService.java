package br.com.orders.service;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrderListByClient;
import br.com.orders.domain.OrdersByClient;

import java.util.List;

public interface OrderService {
    Order findById(Integer orderId);
    Order persistOrder(Order order);
    OrdersByClient findOrderQuantityByClient(Integer clientId);
    OrderListByClient findAllOrdersByClient(Integer clientId);
}
