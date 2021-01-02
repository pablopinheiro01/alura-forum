package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration //habilitamos a leitura dessa classe na inicializacao do Spring
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService service;
	
	//configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//aqui implementamos o processo de autenticacao do projeto
		auth.userDetailsService(service)
		.passwordEncoder(new BCryptPasswordEncoder());//usamos o algoritmo BCrypt suportado pelo spring para trabalhar com a senha de forma segura
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
	
	//criei esse metodo apenas para gerar a senha no hash no formato do Bcrypt
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
	
}
