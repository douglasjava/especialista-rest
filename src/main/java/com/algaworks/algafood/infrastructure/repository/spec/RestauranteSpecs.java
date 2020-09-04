package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RestauranteSpecs {

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<Restaurante> comFreteGratis() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO));
    }

    /**
    public static Specification<Restaurante> comFreteGratis() {
        return new Specification<Restaurante>() {
            @Override
            public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
            }
        };
    }
    **/

}
