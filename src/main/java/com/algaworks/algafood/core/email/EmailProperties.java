package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("algafood.email")
@Getter
@Setter
@Validated
public class EmailProperties {

	@NotNull
	private String remetente;

	@NotNull
	private TipoImplEmail impl = TipoImplEmail.FAKE;

	private Sandbox sandbox = new Sandbox();

	@Getter
	@Setter
	public class Sandbox {
	    
	    private String destinatario;
	    
	}      

}
