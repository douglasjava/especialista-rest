package com.algaworks.algafood;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroGeraisApiIT {

	@LocalServerPort
	int randomServerPort;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = randomServerPort;
		RestAssured.basePath = "/teste";
	}
	
	@Test
	public void deveRetornaStatus200_QuandoConsultar() {
		given()
			.queryParam("page", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/processamento")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
}
