package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.domain.helper.EmailHelper;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * Essa classe poderia ser utilizado dessa forma "extends SmtpEnvioEmailService"
 * então sobreescreveriamos o método enviar da mesma forma que foi feito abaixo
 * porém o método processarTemplate poderia ser utilizado apenas trocando o
 * modificador para protected
 * 
 * @author Marques
 *
 */
@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

	@Autowired
	private EmailHelper emailHelper;

	@Override
	public void enviar(Mensagem mensagem) {

		try {

			String corpo = emailHelper.processarTemplate(mensagem);

			log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);

		} catch (Exception e) {
			throw new EmailException("Não foi possivel enviar e-mail fake", e);

		}

	}

}
