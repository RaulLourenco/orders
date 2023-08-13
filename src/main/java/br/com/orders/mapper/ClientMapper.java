package br.com.orders.mapper;

import br.com.orders.domain.Client;
import br.com.orders.domain.Order;
import br.com.orders.repository.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client mapFrom(ClientEntity clientEntity);

    ClientEntity mapFrom(Client client);

    @Mapping(target = "id", source = "clientCode")
    Client mapFrom(Order order);
}
