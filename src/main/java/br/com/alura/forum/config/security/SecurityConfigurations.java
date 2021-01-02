package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration //habilitamos a leitura dessa classe na inicializacao do Spring
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	
	//configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
	//configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll() //permite qualquer um
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll() //permite qualquer um
		.anyRequest().authenticated()//para as demais url's e necessario estar autenticado
		.and().formLogin();//usa o formulario padrao do Spring
	}
	
	//configuracao de recursos estaticos (requisicoes para js, css, imagens etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
	
	
}
