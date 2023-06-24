package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Person;

public interface PersonService {
	
	Person save(Person person);
	List<Person> listAll();
	Person update(Person person);
	void delete(Integer id);
	List<Person> findByNameContainsIgnoreCase(String name);
	List<Person> findBytype(String type);
	Person findByEmail(String email);

}
