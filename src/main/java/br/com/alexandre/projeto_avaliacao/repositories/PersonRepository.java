package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

	List<Person> findByNameContainsIgnoreCase(String name);
	Person findByEmail(String email);
	List<Person> findByType(String type);
	
}
