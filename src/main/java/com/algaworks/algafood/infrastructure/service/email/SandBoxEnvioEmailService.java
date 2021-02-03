package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.helper.EmailHelper;
import com.algaworks.algafood.domain.service.EnvioEmailServiceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SandBoxEnvioEmailService implements EnvioEmailServiceService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private EmailHelper emailHelper;

	@Override
	public void enviar(Mensagem mensagem) {

		try {

			String corpo = emailHelper.processarTemplate(mensagem);
			String destinatarioFake = emailProperties.getSandbox().getDestinatario();

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = emailHelper.getMimeMessageHelper(mensagem, corpo, mimeMessage);
			helper.setTo(destinatarioFake);

			mailSender.send(helper.getMimeMessage());	
			
			log.info("E-mail enviado para o e-mail {}", destinatarioFake);
			
		} catch (Exception e) {
			throw new EmailException("Não foi possivel enviar e-mail", e);

		}

	}

}
