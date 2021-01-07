package com.devsuperior.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsdeliver.entities.Product;

// Já traz algumas implementações padrão para buscar, salvar, deletar, atualizar dados para alguma entidade
// Long é o tipo da primary key do Product
public interface ProductRepository extends JpaRepository<Product, Long>{
	// Depois ver documentação do Spring Data JPA para ver mais métodos disponíveis
	// Detalhe que o ..byName, esse 'Name' se refere a propriedade 'name' do Product
	// Vai trazer ordenado por nome
	List<Product> findAllByOrderByNameAsc();
}
