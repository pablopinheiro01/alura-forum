package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.alura.forum.modelo.Curso;

@DataJpaTest
public class CursoRepositoryTest {

	@Autowired//como estamos utilizando spring eu consigo injetar qualquer componente gerenciada pelo Spring
	private CursoRepository repository;
	
	@Test
	public void deveriaCarregarCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		Curso curso = repository.findByNome(nomeCurso);
		
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
		
	}
	
	@Test
	public void naoDeveriaCarregarCursoNaoCadastrado() {
		String nomeCurso = "JPA";
		Curso curso = repository.findByNome(nomeCurso);
		
		assertNull(curso);
		
	}

}
