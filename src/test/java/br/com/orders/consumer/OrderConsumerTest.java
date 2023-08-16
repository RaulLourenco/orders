package br.com.orders.consumer;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.domain.OrderItems;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.service.ClientService;
import br.com.orders.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Teste do Consumer de Pedidos")
@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @InjectMocks
    private OrderConsumer consumer;

    @Mock
    private OrderService orderService;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Validator validator;

    private Order order;

    private Client client;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        order.setId(123);
        order.setClientCode(456);
        OrderItems items = new OrderItems();
        items.setProduct("Nutella");
        items.setQuantity(5);
        items.setPrice(BigDecimal.valueOf(10.20));
        order.setItems(List.of(items));

        client = new Client();
        client.setId(456);

    }

    @Test
    void testSuccessConsumer() throws JsonProcessingException {
        final String orderString = order.toString();

        when(objectMapper.readValue(eq(orderString), eq(Order.class))).thenReturn(order);
        when(clientMapper.mapFrom(eq(order))).thenReturn(client);
        when(orderService.persistOrder(eq(order))).thenReturn(order);

        consumer.receive(orderString);

        verify(objectMapper, atLeastOnce()).readValue(eq(orderString), eq(Order.class));
        verify(validator, atLeastOnce()).validate(eq(order));
        verify(clientMapper, atLeastOnce()).mapFrom(eq(order));
        verify(clientService, atLeastOnce()).persistClient(eq(client));
        verify(orderService, atLeastOnce()).persistOrder(eq(order));
    }

    @Test
    void testFailedConsumer() throws JsonProcessingException {
        final String emptyOrder = "";

        when(objectMapper.readValue(eq(emptyOrder), eq(Order.class))).thenThrow(JsonProcessingException.class);

        consumer.receive(emptyOrder);

        verify(objectMapper, atLeastOnce()).readValue(eq(emptyOrder), eq(Order.class));
        verify(validator, never()).validate(eq(order));
        verify(clientMapper, never()).mapFrom(eq(order));
        verify(clientService, never()).persistClient(eq(client));
        verify(orderService, never()).persistOrder(eq(order));
    }
}
