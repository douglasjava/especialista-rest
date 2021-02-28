package com.algaworks.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * "/**" -> Qualquer caminho o spring vai habilitar o cors
	 * 
	 * @apiNote allowedOrigins -> pode especificar uma URL especifica
	 * @apiNote allowedMethods -> pode especificar metodos, o padrão são as
	 *          requisições simples [GET/HEAD/POST]
	 * @apiNote maxAge -> Defini o empo que as requisiçoes ficaram no cache o padrão
	 *          é 1800s 30 minutos
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*");

	}

	/**
	 * Implementação para habilitar ETAG para gerar e validar automaticamente o hash
	 * que vier no cabeçalho da requisição.
	 * 
	 * @return
	 */
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

}
