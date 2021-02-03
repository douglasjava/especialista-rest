package com.algaworks.algafood.core.email;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

@Component
public class OffsetDateTimeFormat implements TemplateMethodModelEx {

	private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		TemporalAccessor dateTime = DateTimeFormatter.ISO_DATE_TIME.parse(arguments.get(0).toString());
		return FORMATTER.format(dateTime);
	}

}
