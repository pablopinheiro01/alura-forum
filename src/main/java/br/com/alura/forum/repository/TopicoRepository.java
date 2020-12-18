package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//o spring consegue utilizar esse metodo para montar a query sozinho, nao preciso digitar a query pelo jpql
	List<Topico> findByTitulo(String nomeCurso);

	// Curso é a entidade de relacionamento e nome e a propriedade da entidade, por este motivo conseguimos mapear
	// o Spring consegue entender o relacionamento e a hierarquia
	//List<Topico> findByCursoNome(String nomeCurso);
	//posso usar o _ para indicar o relaciomaneto e resolver problema de ambiguidade caso atributo esteja em conflito com o relacionamento.
	List<Topico> findByCurso_Nome(String nomeCurso);
	
	//caso eu queira utilizar o meu padrao de metodo e nao o padrao de nomenclatura do spring eu tenho que utilizar o jpql para criar a query
	/*@Query("select t from topico t where t.curso.nome = :nomeCurso")
	List<Topico> procuraCurso(@Param("nomeCurso") String nomeCurso);*/

}
