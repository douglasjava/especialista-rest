package com.algaworks.algafood.domain.helper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.core.email.OffsetDateTimeFormat;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import com.algaworks.algafood.infrastructure.service.email.EmailException;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class EmailHelper {

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
    private OffsetDateTimeFormat offsetDateTimeFormat;

	public String processarTemplate(Mensagem mensagem) {
		try {

			freemarkerConfig.setSharedVariable("offsetDateTimeFormat", offsetDateTimeFormat);
			
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());

		} catch (Exception e) {
			throw new EmailException("Não foi possivel montar o template do e-mail.", e);

		}

	}

	public MimeMessageHelper getMimeMessageHelper(Mensagem mensagem, String corpo, MimeMessage mimeMessage) throws MessagingException {

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setFrom(emailProperties.getRemetente());

		return helper;
	}

}
