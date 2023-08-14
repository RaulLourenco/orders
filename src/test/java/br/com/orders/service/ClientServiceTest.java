package br.com.orders.service;

import br.com.orders.domain.Client;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.repository.ClientRepository;
import br.com.orders.repository.entity.ClientEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Teste do Service de Clientes")
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService service = new ClientServiceImpl();

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper mapper;

    private Client client;

    private ClientEntity entity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(123);

        entity = new ClientEntity();
        entity.setId(123);
    }

    @Test
    void testPersistClient() {
        when(mapper.mapFrom(eq(client))).thenReturn(entity);
        when(clientRepository.save(eq(entity))).thenReturn(entity);
        when(mapper.mapFrom(eq(entity))).thenReturn(client);

        final Client response = service.persistClient(client);

        Assertions.assertEquals(client, response);

        verify(mapper, atLeastOnce()).mapFrom(eq(client));
        verify(clientRepository, atLeastOnce()).save(eq(entity));
        verify(mapper, atLeastOnce()).mapFrom(eq(entity));
    }
}
