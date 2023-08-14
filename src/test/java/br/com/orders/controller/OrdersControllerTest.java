package br.com.orders.controller;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.domain.OrderItems;
import br.com.orders.dto.*;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.mapper.OrderMapper;
import br.com.orders.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Teste do Controller de Pedidos")
@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

    @InjectMocks
    private OrdersController controller;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ClientMapper clientMapper;

    private Order order;

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
    }

    @Test
    void testGetTotalValue() {
        TotalValueResponse response = new TotalValueResponse();
        response.setOrderId(order.getId());
        response.setTotalPrice(BigDecimal.valueOf(10.20).multiply(BigDecimal.valueOf(5)));

        final int orderId = 123;

        when(orderService.findById(eq(orderId))).thenReturn(order);
        when(orderMapper.mapFromOrder(eq(order))).thenReturn(response);

        final ResponseEntity<TotalValueResponse> totalValue = controller.getTotalValue(orderId);

        Assertions.assertEquals(totalValue.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(totalValue.getBody(), response);

        verify(orderService, atLeastOnce()).findById(eq(orderId));
        verify(orderMapper, atLeastOnce()).mapFromOrder(eq(order));
    }

    @Test
    void testGetTotalQuantity() {
        final int clientId = 456;

        Client client = new Client();
        client.setId(clientId);
        client.setOrderQuantity(1);

        TotalQuantityByClientResponse response = new TotalQuantityByClientResponse();
        response.setClientId(clientId);
        response.setOrderQuantity(1);

        when(orderService.findOrderQuantityByClient(eq(clientId))).thenReturn(client);
        when(clientMapper.mapToResponse(eq(client))).thenReturn(response);

        final ResponseEntity<TotalQuantityByClientResponse> totalValue = controller.getTotalQuantity(clientId);

        Assertions.assertEquals(totalValue.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(totalValue.getBody(), response);

        verify(orderService, atLeastOnce()).findOrderQuantityByClient(eq(clientId));
        verify(clientMapper, atLeastOnce()).mapToResponse(eq(client));
    }

    @Test
    void testGetOrdersList() {
        final int clientId = 456;

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setClientCode(order.getClientCode());

        OrderItemsDto itemsDto = new OrderItemsDto();
        itemsDto.setProduct(order.getItems().get(0).getProduct());
        itemsDto.setQuantity(order.getItems().get(0).getQuantity());
        itemsDto.setPrice(order.getItems().get(0).getPrice());

        orderDto.setItems(List.of(itemsDto));

        OrderListByClientResponse response = new OrderListByClientResponse();
        response.setClientId(clientId);
        final List<OrderDto> orderListDto = List.of(orderDto);
        response.setOrders(orderListDto);

        final List<Order> orderList = List.of(order);

        when(orderService.findAllOrdersByClient(eq(clientId))).thenReturn(orderList);
        when(orderMapper.mapToResponse(eq(orderList))).thenReturn(orderListDto);

        final ResponseEntity<OrderListByClientResponse> totalValue = controller.getOrdersList(clientId);

        Assertions.assertEquals(totalValue.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(totalValue.getBody(), response);

        verify(orderService, atLeastOnce()).findAllOrdersByClient(eq(clientId));
        verify(orderMapper, atLeastOnce()).mapToResponse(eq(orderList));
    }
}
