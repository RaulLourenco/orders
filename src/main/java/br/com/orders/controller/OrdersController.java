package br.com.orders.controller;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.dto.OrderListByClientResponse;
import br.com.orders.dto.TotalQuantityByClientResponse;
import br.com.orders.dto.TotalValueResponse;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.mapper.OrderMapper;
import br.com.orders.service.ClientService;
import br.com.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ClientMapper clientMapper;

    private static Logger log = LoggerFactory.getLogger(OrdersController.class);

    @PostMapping("/saveOrder")
    public Order saveOrder(@RequestBody Order order) {
        log.info("Criando pedido na base de dados. Pedido={}", order);

        Client client = clientMapper.mapFrom(order);
        clientService.persistClient(client);

        Order persisted = orderService.persistOrder(order);

        log.info("Pedido persistido na base de dados. Pedido={}", persisted);

        return persisted;
    }

    @GetMapping("/total_value/{orderId}")
    public ResponseEntity<TotalValueResponse> getTotalValue(@PathVariable final int orderId) {
        log.info("Buscando pedido na base de dados. ID do Pedido={}", orderId);

        Order order = orderService.findById(orderId);

        TotalValueResponse response = orderMapper.mapFromOrder(order);

        log.info("Response de valor total retornado com sucesso. Response={}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/total_quantity/{clientId}")
    public ResponseEntity<TotalQuantityByClientResponse> getTotalQuantity(@PathVariable final int clientId) {
        log.info("Buscando quantidade de pedidos por cliente. ID do Cliente={}", clientId);

        Client orderQuantityByClient = orderService.findOrderQuantityByClient(clientId);

        TotalQuantityByClientResponse response = clientMapper.mapToResponse(orderQuantityByClient);

        log.info("Response de quantidade total retornado com sucesso. Response={}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/orders/{clientId}")
    public ResponseEntity<OrderListByClientResponse> getOrdersList(@PathVariable final int clientId) {
        log.info("Buscando lista de pedidos por cliente. ID do Cliente={}", clientId);

        List<Order> allOrdersByClient = orderService.findAllOrdersByClient(clientId);

        OrderListByClientResponse response = getResponse(allOrdersByClient);

        log.info("Response de lista de pedidos retornado com sucesso. Response={}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private OrderListByClientResponse getResponse(final List<Order> list) {
        OrderListByClientResponse response = new OrderListByClientResponse();
        if(!list.isEmpty()) {
            response.setClientId(list.stream().findFirst().get().getClientCode());
            response.setOrders(orderMapper.mapToResponse(list));
        }
        return response;
    }
}
