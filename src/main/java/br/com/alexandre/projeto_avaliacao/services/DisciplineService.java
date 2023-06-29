package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

public interface DisciplineService {

	Discipline save(Discipline discipline);

	Discipline update(Discipline discipline);

	void delete(Integer id);

	Discipline findById(Integer id);

	List<Discipline> listAll();

	List<Discipline> findByNameContainsIgnoreCase(String nome);

	List<Discipline> findByCourse(Course course);
}
