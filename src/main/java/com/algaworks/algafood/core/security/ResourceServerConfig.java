package com.algaworks.algafood.core.security;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		//.authorizeRequests()
	    		//.antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
				//.antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
				//.antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
				//.anyRequest().denyAll()
			//.and()
    		.csrf().disable()
    		.cors().and()
    		.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    		//.oauth2ResourceServer().opaqueToken();//habilitando o Resorce Serve no projeto para validar token opaco    		
    }

    /** Chave secreta assimetrica
    @Bean
    public JwtDecoder jwtDecoder() {
    	var secret = new SecretKeySpec("jasjdadkadabdakdhadha544555544564sadada5d4ad5ad4ad".getBytes(), "HmacSHA256");
    	return NimbusJwtDecoder.withSecretKey(secret).build();
    }
    */
    
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			return authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		});
		
		return jwtAuthenticationConverter;
	}
    
}
