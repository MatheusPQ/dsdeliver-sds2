package com.devsuperior.dsdeliver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service; // O controller depende do service, o service depende do repository

	@GetMapping // Endpoint GET
	public ResponseEntity<List<ProductDTO>> findAll(){ // vai encapsular uma resposta de uma requisição http
		List<ProductDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list); // Código HTTP 200
	}
}
