package br.com.alura.forum.config.security;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repo;
	
	//metodo que e chamado no processo de login
	// a senha no spring e feita a checagem da senha em memoria
	//apenas consultamos o e-mail no banco de dados
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = repo.findByEmail(username);
		if(user.isPresent()) {
			return user.get();
		}
		//caso nao encontre o usuario
		throw new UsernameNotFoundException("Dados invalidos");
	}
	
}
