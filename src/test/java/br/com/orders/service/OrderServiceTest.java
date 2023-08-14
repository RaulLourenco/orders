package br.com.orders.service;

import br.com.orders.application.Constantes;
import br.com.orders.application.exceptions.OrderNotFoundException;
import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.domain.OrderItems;
import br.com.orders.mapper.OrderMapper;
import br.com.orders.repository.OrderRepository;
import br.com.orders.repository.entity.OrderEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Teste do Service de Pedidos")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service = new OrderServiceImpl();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper mapper;

    private Order order;

    private OrderEntity orderEntity;

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

        orderEntity = new OrderEntity();
        orderEntity.setId(123);
        orderEntity.setClientCode(456);
        orderEntity.setItems(List.of(items));
    }

    @Test
    void testSuccessFindById() {
        final int orderId = 123;

        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.of(orderEntity));
        when(mapper.mapFrom(eq(orderEntity))).thenReturn(order);

        final Order response = service.findById(orderId);

        Assertions.assertEquals(order, response);

        verify(orderRepository, atLeastOnce()).findById(eq(orderId));
        verify(mapper, atLeastOnce()).mapFrom(eq(orderEntity));
    }

    @Test
    void testFailedFindById() {
        final int orderId = 123;

        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.empty());

        final OrderNotFoundException exception = Assertions.assertThrows(
                OrderNotFoundException.class, () -> service.findById(orderId));

        Assertions.assertEquals(exception.getMessage(),
                String.format("%s: %s", Constantes.ORDER_NOT_FOUND_MESSAGE, orderId));

        verify(orderRepository, atLeastOnce()).findById(eq(orderId));
        verify(mapper, never()).mapFrom(eq(orderEntity));
    }

    @Test
    void testPersistOrder() {
        when(mapper.mapFrom(eq(order))).thenReturn(orderEntity);
        when(orderRepository.save(eq(orderEntity))).thenReturn(orderEntity);
        when(mapper.mapFrom(eq(orderEntity))).thenReturn(order);

        final Order response = service.persistOrder(order);

        Assertions.assertEquals(order, response);

        verify(mapper, atLeastOnce()).mapFrom(eq(order));
        verify(orderRepository, atLeastOnce()).save(eq(orderEntity));
        verify(mapper, atLeastOnce()).mapFrom(eq(orderEntity));
    }

    @Test
    void testSuccessFindOrderQuantityByClient() {
        final int clientId = 456;

        Client client = new Client();
        client.setId(clientId);
        client.setOrderQuantity(1);

        when(orderRepository.findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15))))
                .thenReturn(List.of(orderEntity));

        final Client response = service.findOrderQuantityByClient(clientId);

        Assertions.assertEquals(client, response);

        verify(orderRepository, atLeastOnce()).findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15)));
    }

    @Test
    void testFailedFindOrderQuantityByClient() {
        final int clientId = 456;

        when(orderRepository.findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15))))
                .thenReturn(List.of());

        final OrderNotFoundException exception = Assertions.assertThrows(
                OrderNotFoundException.class, () -> service.findOrderQuantityByClient(clientId));

        Assertions.assertEquals(String.format("%s: %s",
                Constantes.ORDER_LIST_NOT_FOUND_MESSAGE, clientId),
                exception.getMessage());

        verify(orderRepository, atLeastOnce()).findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15)));
    }

    @Test
    void testSuccessFindAllOrdersByClient() {
        final int clientId = 456;

        when(orderRepository.findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15))))
                .thenReturn(List.of(orderEntity));
        when(mapper.mapFrom(eq(orderEntity))).thenReturn(order);

        final List<Order> response = service.findAllOrdersByClient(clientId);

        Assertions.assertEquals(List.of(order), response);
        Assertions.assertEquals(1, response.size());

        verify(orderRepository, atLeastOnce()).findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15)));
        verify(mapper, atLeastOnce()).mapFrom(eq(orderEntity));
    }

    @Test
    void testFailedFindAllOrdersByClient() {
        final int clientId = 456;

        when(orderRepository.findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15))))
                .thenReturn(List.of());

        final OrderNotFoundException exception = Assertions.assertThrows(
                OrderNotFoundException.class, () -> service.findAllOrdersByClient(clientId));

        Assertions.assertEquals(String.format("%s: %s",
                Constantes.ORDER_LIST_NOT_FOUND_MESSAGE, clientId), exception.getMessage());

        verify(orderRepository, atLeastOnce()).findAllOrdersByClient(eq(clientId), eq(PageRequest.of(0, 15)));
        verify(mapper, never()).mapFrom(eq(orderEntity));
    }
}
