package br.com.alura.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)//anotacao para rodar com o junit 5, os imports devem ser do package "jupiter"
//caso eu usasse somente essa anotacao eu teria somente as classes mvc
//@WebMvcTest //teste da camada controller precisa dessa anotacao que ira carregar a camada mvc do spring para testes
@SpringBootTest //essa anotacao carrega tudo do Spring para usarmos.
@AutoConfigureMockMvc //preciso para configurar o mockvc no teste
@ActiveProfiles("test")//essa anotacao forca o profile no spring para que o ambiente seja o configurado no caso teste
public class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mock;
	
	@Test
	public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
		
		//o que eu preciso?
		//url
		//corpo da requisicao
		URI uri = new URI("/auth");
		String json = "{\"email\":\"invalido@email.com\",\"senha\":\"123456\"}";
		
		mock
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
		
	}

}
