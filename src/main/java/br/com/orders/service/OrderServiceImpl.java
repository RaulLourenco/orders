package br.com.orders.service;

import br.com.orders.application.Constantes;
import br.com.orders.application.exceptions.OrderNotFoundException;
import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.mapper.OrderMapper;
import br.com.orders.repository.OrderRepository;
import br.com.orders.repository.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper mapper;

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order findById(final Integer orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);

        if(orderEntity.isEmpty()) {
            throw new OrderNotFoundException(String.format("%s: %s", Constantes.ORDER_NOT_FOUND_MESSAGE, orderId));
        }

        return mapper.mapFrom(orderEntity.get());
    }

    @Override
    public Order persistOrder(final Order order) {
        log.info("Iniciando persistencia do pedido na base. Pedido={}", order);

        final BigDecimal totalPrice = order.getItems().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        OrderEntity orderEntity = orderRepository.save(mapper.mapFrom(order));

        log.info("Persistencia do pedido feito com sucesso! Pedido={}", orderEntity);

        return mapper.mapFrom(orderEntity);
    }

    @Override
    public Client findOrderQuantityByClient(final Integer clientId) {
        log.info("Iniciando busca da quantidade de pedidos por cliente.");

        List<OrderEntity> orders = getOrderEntityList(clientId);

        log.info("Busca realizada com sucesso.");

        Client client = new Client();
        client.setId(clientId);
        client.setOrderQuantity(orders.size());

        return client;
    }

    @Override
    public List<Order> findAllOrdersByClient(final Integer clientId) {
        log.info("Iniciando busca da lista de pedidos por cliente.");

        List<OrderEntity> orders = getOrderEntityList(clientId);

        log.info("Busca realizada com sucesso.");

        List<Order> list = new ArrayList<>();
        orders.forEach(order -> list.add(mapper.mapFrom(order)));

        return list;
    }

    private List<OrderEntity> getOrderEntityList(final Integer clientId) {
        List<OrderEntity> orders = orderRepository.findAllOrdersByClient(clientId, PageRequest.of(0,15));
        if(orders.isEmpty()) {
            throw new OrderNotFoundException(String.format("%s: %s", Constantes.ORDER_LIST_NOT_FOUND_MESSAGE, clientId));
        }
        return orders;
    }
}
