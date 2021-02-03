package com.algaworks.algafood.infrastructure.service.query;

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
    public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {

        var predicates = new ArrayList<>();
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);// Mapear o retorno
        var root = query.from(Pedido.class); //Mapear o from

        // Função criada para permitir escolher qual timeZone o usuário quer o retorno das datas
        // função para mySQL
        var functionConvertTzDataCriacao =
                builder.function("convert_tz", Date.class, root.get("dataCriacao"),
                        builder.literal("+00:00"), builder.literal(timeOffset));


        //Criada a função nativa do mysql, utilizando date para truncar a data retirando hh:mm:ss
        //Observação, ainda não está sendo possivel retornar LocalData
        var functionDateDataCriacao =
                builder.function("date", Date.class, functionConvertTzDataCriacao);

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
