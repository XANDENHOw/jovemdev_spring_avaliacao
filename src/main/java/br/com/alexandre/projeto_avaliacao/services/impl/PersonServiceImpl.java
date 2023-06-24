package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Person;
import br.com.alexandre.projeto_avaliacao.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService{

	@Override
	public Person save(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Person> findByNameContainsIgnoreCase(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> findBytype(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
