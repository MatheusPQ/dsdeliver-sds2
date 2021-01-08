package com.devsuperior.dsdeliver.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService service;

	@GetMapping // Endpoint GET
	public ResponseEntity<List<OrderDTO>> findAll() { // vai encapsular uma resposta de uma requisição http
		List<OrderDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list); // Código HTTP 200
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto) { // O framework vai converter o JSON no objeto OrderDTO com a anotação @RequestBody
		dto = service.insert(dto);
//		return ResponseEntity.ok().body(dto); // Poderia fazer assim tbm, mas não é ideal
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri(); // Cria a URI que corresponde ao recurso criado
		return ResponseEntity.created(uri).body(dto); // Código HTTP 201 - Created
	}
	
	@PutMapping("/{id}/delivered")
	public ResponseEntity<OrderDTO> setDelivered(@PathVariable Long id) { // @PathVariable para o parâmetro da URL ficar atrelado a este parâmetro na função
		OrderDTO dto = service.setDelivered(id);
		
		return ResponseEntity.ok().body(dto); // Código HTTP 200
	}
}
