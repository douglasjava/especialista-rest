package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.domain.model.dto.ValidateDeepDto;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FormaPagamentoService {

	private FormaPagamentoRepository formaPagamentoRepository;

	public ValidateDeepDto validateDeepEtags(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}

		return ValidateDeepDto.builder().checkNotModified(request.checkNotModified(eTag)).etag(eTag).build();

	}
	
	public ValidateDeepDto validateDeepEtags(ServletWebRequest request, Long formaPagamentoId) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(formaPagamentoId);

		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}

		return ValidateDeepDto.builder().checkNotModified(request.checkNotModified(eTag)).etag(eTag).build();

	}

}
