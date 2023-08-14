package br.com.orders.consumer;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.service.ClientService;
import br.com.orders.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private static Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload final String message) {
        log.info("Criando pedido na base de dados. Pedido={}", message);
        try {
            final Order order = objectMapper.readValue(message, Order.class);
            final Client client = clientMapper.mapFrom(order);
            clientService.persistClient(client);

            final Order persisted = orderService.persistOrder(order);

            log.info("Pedido persistido na base de dados. Pedido={}", persisted);
        } catch(final JsonProcessingException ex) {
            log.error("Erro ao converter mensagem para pedido");
        }
    }
}
