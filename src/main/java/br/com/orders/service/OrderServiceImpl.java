package br.com.orders.service;

import br.com.orders.domain.Order;
import br.com.orders.domain.OrdersByClient;
import br.com.orders.mapper.OrderMapper;
import br.com.orders.repository.OrderRepository;
import br.com.orders.repository.entity.OrderEntity;
import br.com.orders.repository.entity.OrdersByClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Optional<OrderEntity> order = orderRepository.findById(orderId);

        return order.map(orderEntity -> mapper.mapFrom(orderEntity)).orElse(null);
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
    public OrdersByClient findAllOrdersByClient(final Integer clientId) {
        log.info("Iniciando busca da quantidade de pedidos por cliente.");

        List<OrderEntity> orders = orderRepository.findAllOrdersByClient(clientId, PageRequest.of(0,15));

        log.info("Busca realizada com sucesso.");

        OrdersByClientEntity response = new OrdersByClientEntity();
        response.setClientId(clientId);
        response.setOrderQuantity(orders.size());

        return mapper.mapFrom(response);
    }
}
