package br.com.orders.controller;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.domain.OrdersByClient;
import br.com.orders.dto.TotalAmountByClientResponse;
import br.com.orders.dto.TotalValueResponse;
import br.com.orders.service.ClientService;
import br.com.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    private static Logger log = LoggerFactory.getLogger(OrdersController.class);

    @PostMapping("/saveOrder")
    public Order saveOrder(@RequestBody Order order) {
        log.info("Criando pedido na base de dados. Pedido={}", order);

        Client client = new Client();
        client.setId(order.getClientCode());
        clientService.persistClient(client);

        Order persisted = orderService.persistOrder(order);

        log.info("Pedido persistido na base de dados. Pedido={}", persisted);

        return persisted;
    }

    @GetMapping("/total_value/{orderId}")
    public ResponseEntity<TotalValueResponse> getTotalValue(@PathVariable int orderId) {
        log.info("Buscando pedido na base de dados. ID do Pedido={}", orderId);

        Order order = orderService.findById(orderId);

        TotalValueResponse response = new TotalValueResponse();
        response.setOrderId(order.getId());
        response.setTotalPrice(order.getTotalPrice());

        log.info("Response de valor total retornado com sucesso. Response={}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/total_amount/{clientId}")
    public ResponseEntity<TotalAmountByClientResponse> getTotalAmount(@PathVariable int clientId) {
        log.info("Buscando quantidade de pedidos por cliente. ID do Cliente={}", clientId);

        OrdersByClient allOrdersByClient = orderService.findAllOrdersByClient(clientId);

        TotalAmountByClientResponse response = new TotalAmountByClientResponse();
        response.setClientId(allOrdersByClient.getClientId());
        response.setOrdersAmount(allOrdersByClient.getOrdersAmount());

        log.info("Response de quantidade total retornado com sucesso. Response={}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
