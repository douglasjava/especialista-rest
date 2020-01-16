package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {		
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinhaOpt = cozinhaRepository.findById(cozinhaId);
		
		if(!cozinhaOpt.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinhaOpt.get());
		
		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long cidadeId) {
		try {

			Optional<Restaurante> restauranteOptional = restauranteRepository.findById(cidadeId);

			if (restauranteOptional.isEmpty()) {
				throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de restaurante com código %d", cidadeId));
			}

			restauranteRepository.delete(restauranteOptional.get());

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removida, pois está em uso", cidadeId));
		}

	}
	

}
