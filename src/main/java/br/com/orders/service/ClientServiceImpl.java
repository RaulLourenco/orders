package br.com.orders.service;

import br.com.orders.domain.Client;
import br.com.orders.mapper.ClientMapper;
import br.com.orders.repository.ClientRepository;
import br.com.orders.repository.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper mapper;

    @Override
    public Client persistClient(Client client) {
        ClientEntity clientEntity = clientRepository.save(mapper.mapFrom(client));
        return mapper.mapFrom(clientEntity);
    }
}
