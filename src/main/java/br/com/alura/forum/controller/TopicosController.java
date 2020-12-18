package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@RequestMapping("/topicos")
	//@ResponseBody com a anotacao do RestController o Spring identifica que todos os metodos vão ter o responseBody
	public List<TopicoDto> lista(String nomeCurso) {
		List<Topico> topicos;
		if(nomeCurso == null) {
			topicos = topicoRepository.findAll();
		}else {
			topicos = topicoRepository.findByCurso_Nome(nomeCurso);
		}
		return TopicoDto.converter(topicos);
	}

}
