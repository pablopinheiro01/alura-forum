package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	//@ResponseBody com a anotacao do RestController o Spring identifica que todos os metodos vão ter o responseBody
	//DTO - Usamos o padrao para quando os dados saem para o cliente
	public Page<TopicoDto> lista(
			@RequestParam(required = false) String nomeCurso,
			@RequestParam(required = true) Integer pagina,
			@RequestParam(name = "qtd", required = true) Integer quantidadeDeElementosPorPagina
			){
		
		Page<Topico> topicos;
		
		Pageable paginacao = PageRequest.of(pagina, quantidadeDeElementosPorPagina);
		
		if(nomeCurso == null) {
			topicos = topicoRepository.findAll(paginacao);
		}else {
			topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
		}
		return TopicoDto.converter(topicos);
	}
	
	@PostMapping	
	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita
	//uriBuilder e injetado pelo Spring para nos
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")//por padrao o mesmo nome do dado recebido na url o spring interpreta no parametro recebido
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		//Topico topico = topicoRepository.getOne(id); //getOne por padrao retorna um erro caso nao encontre o recurso
		Optional<Topico> topico = topicoRepository.findById(id); 
		
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{id}")
	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
		Optional<Topico> optional = topicoRepository.findById(id); 
		
		if(optional.isPresent()) {
			//esta linha é auto gerenciada pela JPA nao e necessario atualizar o banco de dados com algum metodo devido o seu auto gerenciamento.
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
			
	}
	
	@DeleteMapping("/{id}")
	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = topicoRepository.findById(id); 
		
		if(topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
		
	}

}
