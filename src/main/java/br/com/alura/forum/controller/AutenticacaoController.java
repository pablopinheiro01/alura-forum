package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;

//logica de autenticacao do jwt
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	//essa classe por algum motivo nao vem configurada para fazer a injecao de dependencia no spring
	//devemos configurar ela no SecurityConfigurations
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){
		
		try {
			UsernamePasswordAuthenticationToken dadosLogin = form.converter();
			//realiza a autenticacao
			Authentication authenticate = authManager.authenticate(dadosLogin);
			
			String token = tokenService.gerarToken(authenticate);
			
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		}catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
