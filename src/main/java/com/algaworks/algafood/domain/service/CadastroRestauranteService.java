package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida, pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void excluir(Long cidadeId) {
		try {

			Restaurante restaurante = buscarOuFalhar(cidadeId);

			restauranteRepository.delete(restaurante);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, cidadeId));
		}

	}

	public Restaurante buscarOuFalhar(Long cidadeId) {
		return restauranteRepository.findById(cidadeId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(cidadeId));
	}

}
