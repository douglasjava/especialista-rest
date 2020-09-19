package com.algaworks.algafood.infrastructure.repository.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    private EntityManager entityManager;

    public VendaQueryServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter vendaDiariaFilter) {

        var predicates = new ArrayList<>();
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);// Mapear o retorno
        var root = query.from(Pedido.class); //Mapear o from

        //Criada a função nativa do mysql, utilizando date para truncar a data retirando hh:mm:ss
        //Observação, ainda não está sendo possivel retornar LocalData
        var functionDateDataCriacao =
                builder.function("date", Date.class, root.get("dataCriacao"));

        // O select a ser feito
        // passando a função e funções do proprio builder
        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        if (vendaDiariaFilter.getRestauranteId() != null) {
            predicates.add(builder.equal(
                    root.get("restaurante"), vendaDiariaFilter.getRestauranteId()));
        }

        if (vendaDiariaFilter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoInicio()));
        }

        if (vendaDiariaFilter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoFim()));
        }

        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

}
