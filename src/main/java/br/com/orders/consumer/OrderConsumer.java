package br.com.orders.consumer;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.domain.OrderItems;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private Validator validator;

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload final String message) {
        log.info("Criando pedido na base de dados. Pedido={}", message);
        try {
            final Order order = objectMapper.readValue(message, Order.class);

            validate(order);

            createClient(order);

            final Order orderCreated = createOrder(order);

            log.info("Pedido persistido na base de dados. Pedido={}", orderCreated);
        } catch(final Throwable ex) {
            if(ex instanceof JsonProcessingException) {
                log.error("Erro ao converter mensagem para pedido.");
            } else {
                log.error("Erro ao validar/processar pedido.");
            }
        }
    }

    private void createClient(final Order order) {
        final Client client = clientMapper.mapFrom(order);
        clientService.persistClient(client);
    }

    private Order createOrder(final Order order) {
        return orderService.persistOrder(order);
    }

    private void validate(final Order order) {
        final Set<ConstraintViolation<Order>> violations = validator.validate(order);
        validateList(order.getItems());
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateList(final List<OrderItems> items) {
        for(OrderItems item: items) {
            final Set<ConstraintViolation<OrderItems>> itemViolations = validator.validate(item);
            if(!itemViolations.isEmpty()) {
                throw new ConstraintViolationException(itemViolations);
            }
        }
    }
}
