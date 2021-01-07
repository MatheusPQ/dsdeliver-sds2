package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service // ou @Component. Diz que essa classe é um componente que poderá ser injetada em outros componentes
public class ProductService {
	
	// Lembrando: ProductService(um componente) DEPENDE de outro componente, o ProductRepository
	@Autowired // Ao invés de criar um construtor do ProductService e instanciar nele o repository com 'this.repository = repository', esse autoWired faz isso automaticamente
	private ProductRepository repository;
	
	@Transactional(readOnly = true) // Para não dar lock no banco/garante que fecha a conexão com o banco (operação somente de leitura)
	public List<ProductDTO> findAll() {
		// Converte as entidades para DTOs, objetos que não tem relação com o banco de dados/ORM
		// Para deixar a aplicação mais "separada"
		
		// Repare nos métodos disponíveis no 'repository'!
		List<Product> list = repository.findAllByOrderByNameAsc();
		
		// stream aceita operações de alta ordem/primeira order (?)
		// map para realizar uma operação em cada elemento
		// collect converte a stream de volta pra uma list
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
}
