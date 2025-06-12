package org.serratec.h2.grupo2.controller;

import org.serratec.h2.grupo2.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class ContaController {
	
	@Autowired
	private ContaService service;

	@PostMapping
	public String login(@RequestParam String email, @RequestParam String senha) {
		return service.login(email, senha);
	}
}
