package br.com.alura.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.alura.forum.repository.TopicoRepository;

@Controller
public class ForumController {
	
	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello Wolrd";
	}
 
}
