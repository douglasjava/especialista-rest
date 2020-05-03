package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long estadoId) {
		try {

			Estado estado = estadoRepository.findById(estadoId)
					.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));

			estadoRepository.delete(estado);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}

}
