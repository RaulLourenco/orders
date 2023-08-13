package br.com.orders.repository;

import br.com.orders.repository.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class QueryRepositoryImpl implements QueryRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<OrderEntity> findAllOrdersByClient(final Integer clientId, final Pageable pageable) {
        final Query query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();
        final int zero = 0;
        if(Objects.nonNull(clientId) && clientId > zero) {
            criteria.add(Criteria.where("clientCode").is(clientId));
        }
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return mongoTemplate.find(query, OrderEntity.class);
    }
}
