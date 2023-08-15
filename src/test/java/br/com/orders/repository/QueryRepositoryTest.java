package br.com.orders.repository;

import br.com.orders.domain.OrderItems;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Teste do Query Repository")
@ExtendWith(MockitoExtension.class)
class QueryRepositoryTest {

    @InjectMocks
    private QueryRepository repository = new QueryRepositoryImpl();

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private Query query;

    private OrderEntity orderEntity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        OrderItems items = new OrderItems();
        items.setProduct("Nutella");
        items.setQuantity(5);
        items.setPrice(BigDecimal.valueOf(10.20));

        orderEntity = new OrderEntity();
        orderEntity.setId(123);
        orderEntity.setClientCode(456);
        orderEntity.setItems(List.of(items));
    }

    @Test
    void testQuery() {
        final int clientId = 456;

        when(mongoTemplate.find(any(), any())).thenReturn(List.of(orderEntity));

        List<OrderEntity> allOrdersByClient = repository
                .findAllOrdersByClient(clientId, PageRequest.of(0, 15));

        Assertions.assertEquals(List.of(orderEntity), allOrdersByClient);

        verify(mongoTemplate, atLeastOnce()).find(any(), any());
    }
}
