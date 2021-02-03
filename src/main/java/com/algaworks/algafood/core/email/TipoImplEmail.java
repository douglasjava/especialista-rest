package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailServiceService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandBoxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;

import lombok.Getter;

@Getter
public enum TipoImplEmail {

	FAKE(new FakeEnvioEmailService()), 
	SMTP(new SmtpEnvioEmailService()), 
	SANDBOX(new SandBoxEnvioEmailService());
	
	private EnvioEmailServiceService implementacao;
	
	TipoImplEmail(EnvioEmailServiceService implementacao) {
		this.implementacao = implementacao;
	}

}
