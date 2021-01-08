package com.devsuperior.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsdeliver.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	// Query manual na linguagem da JPA, chamada JPQL
	// Detalhe que 'Order' deve ser exatamente igual ao nome da classe
	// obj é o apelido dos objetos que serão buscados, é obrigatório
	// JOIN FETCH faz o inner join
	// 'products' é o nome da coleção de produtos na classe 'Order'
	@Query("SELECT DISTINCT obj FROM Order obj JOIN FETCH obj.products "
			+ " WHERE obj.status = 0 ORDER BY obj.moment ASC")
	List<Order> findOrdersWithProducts();
}
