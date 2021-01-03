package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration //habilitamos a leitura dessa classe na inicializacao do Spring
@Profile("dev")//carrega essa classe somente se o ambiente for o declarado
public class DevSecurityConfigurations2 extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService service;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//essa e a configuracao feita para habilitar a injecao do AuthenticationManager na minha controller de autenticacao
	//esse metodo esta extendido na classe websecurity...
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
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
		.antMatchers("/**").permitAll() 
		.and().csrf().disable(); //precisamos fazer essa configuracao para desabilitar crownfowarding para configurar o jwt
	}
	
	//configuracao de recursos estaticos (requisicoes para js, css, imagens etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	
	//criei esse metodo apenas para gerar a senha no hash no formato do Bcrypt
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
	
}
