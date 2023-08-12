package br.com.orders.service;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrdersByClient;

public interface OrderService {
    Order findById(Integer orderId);
    Order persistOrder(Order order);
    OrdersByClient findAllOrdersByClient(Integer clientId);
}
