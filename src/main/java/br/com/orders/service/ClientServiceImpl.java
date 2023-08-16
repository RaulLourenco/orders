package br.com.orders.service;

import br.com.orders.domain.Client;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.repository.ClientRepository;
import br.com.orders.repository.entity.ClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Client persistClient(final Client client) {
        log.info("Iniciando persistencia de cliente na base. Cliente={}", client);

        final Optional<ClientEntity> entityOptional = clientRepository.findById(client.getId());

        ClientEntity clientEntity = entityOptional.orElseGet(ClientEntity::new);

        if(entityOptional.isEmpty()) {
            clientEntity = clientRepository.save(mapper.mapFrom(client));
            log.info("Persistencia de cliente feita com sucesso! Cliente={}", clientEntity);
        }

        return mapper.mapFrom(clientEntity);
    }
}
