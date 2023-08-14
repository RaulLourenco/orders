package br.com.orders.service;

import br.com.orders.domain.Client;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.repository.ClientRepository;
import br.com.orders.repository.entity.ClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        final ClientEntity clientEntity = clientRepository.save(mapper.mapFrom(client));

        log.info("Persistencia de cliente feito com sucesso! Cliente={}", clientEntity);

        return mapper.mapFrom(clientEntity);
    }
}
