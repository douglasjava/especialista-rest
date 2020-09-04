package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            var predicates = new ArrayList<>();

            if (Objects.nonNull(filtro.getClienteId())) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (Objects.nonNull(filtro.getRestauranteId())) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if (Objects.nonNull(filtro.getDataCriacaoInicio())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }

            if (Objects.nonNull(filtro.getDataCriacaoFim())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
