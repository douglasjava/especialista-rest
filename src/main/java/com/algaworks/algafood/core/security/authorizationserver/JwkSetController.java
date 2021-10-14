package com.algaworks.algafood.core.security.authorizationserver;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class JwkSetController {

	private JWKSet jwkSet;
	
	@GetMapping("/.well-known/jwks.json")
	public Map<String, Object> keys() {
		System.out.println("Recuperando chave publica ");
		return jwkSet.toJSONObject();
	}
	
}
