package br.com.orders.mapper;

import br.com.orders.domain.Client;
import br.com.orders.repository.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client mapFrom(ClientEntity clientEntity);

    ClientEntity mapFrom(Client client);
}
