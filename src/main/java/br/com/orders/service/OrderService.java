package br.com.orders.service;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;

import java.util.List;

public interface OrderService {
    Order findById(Integer orderId);
    Order persistOrder(Order order);
    Client findOrderQuantityByClient(Integer clientId);
    List<Order> findAllOrdersByClient(Integer clientId);
}
