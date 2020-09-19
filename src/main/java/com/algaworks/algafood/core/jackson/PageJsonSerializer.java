package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

/**
 * Classe criada para mapear a saida do Page, com essa funcionalidade é possível
 * configurar cada atributo que será redenrizado na saída.
 */
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        gen.writeStartObject();

        gen.writeObjectField("lista", page.getContent());
        gen.writeNumberField("tamanho", page.getSize());
        gen.writeNumberField("totalItens", page.getTotalElements());
        gen.writeNumberField("totalPaginas", page.getTotalPages());
        gen.writeNumberField("numeroPaginaAtual", page.getNumber());

        gen.writeEndObject();

    }

}
