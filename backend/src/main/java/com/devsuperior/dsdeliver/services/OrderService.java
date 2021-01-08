package com.devsuperior.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service 
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findOrdersWithProducts();

		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}

	// Método para inserir um pedido
	@Transactional // Não é read-only, pois está fazendo alteração no banco
	public OrderDTO insert(OrderDTO dto) {
		
		// O objeto 'dto' acima vai conter os dados do pedido junto com
		// produtos desse pedido
		
		// Instancia um pedido
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), 
				Instant.now(), OrderStatus.PENDING);
		
		// Não misturar DTO com entidade! Order com ProductDTO, por exemplo
		
		// Varre todos os produtos no dto, chamado cada produto de 'p'
		for(ProductDTO p : dto.getProducts()) {
			
			// Instancia um produto com base no id do 'p'
			Product product = productRepository.getOne(p.getId()); // Não consulta o banco de dados
			
			// Adiciona o produto na lista dos produtos
			order.getProducts().add(product);
		}
		
		order = repository.save(order);
		return new OrderDTO(order);
	}

	// Método para atualizar o status do pedido
	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = repository.getOne(id); // Não acessa o banco de dados, mas instancia um pedido
		order.setStatus(OrderStatus.DELIVERED);
		
		order = repository.save(order); // Salva no banco
		
		return new OrderDTO(order);
	}
	
}
