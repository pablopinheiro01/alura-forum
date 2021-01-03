package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.modelo.Curso;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // aqui indica que nao e para substituir as configuracoes do banco de dados configurado
@ActiveProfiles("test")//essa anotacao forca o profile no spring para que o ambiente seja o configurado no caso teste
public class CursoRepositoryTest {

	@Autowired//como estamos utilizando spring eu consigo injetar qualquer componente gerenciada pelo Spring
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void deveriaCarregarCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		Curso curso = repository.findByNome(nomeCurso);
		
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
		
	}
	
	@Test
	public void deveriaCarregarCursoEspecifico() {
		String nomeCurso = "DEV PRATA";
		Curso curso2 = new Curso();
		curso2.setNome("DEV PRATA");
		curso2.setCategoria("Spring");
		
		em.persist(curso2);
		
		assertNotNull(curso2);
		assertEquals(nomeCurso, curso2.getNome());
		
	}
	
	
	@Test
	public void naoDeveriaCarregarCursoNaoCadastrado() {
		String nomeCurso = "JPA";
		Curso curso = repository.findByNome(nomeCurso);
		
		assertNull(curso);
		
	}

}
