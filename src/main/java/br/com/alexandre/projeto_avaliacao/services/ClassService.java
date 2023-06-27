package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;
import java.util.Optional;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

public interface ClassService {

	Class save(Class studyClass);

	Class update(Class studyClass);

	void delete(Integer id);

	Class findById(Integer id);

	List<Class> listAll();

	Optional<Class> findByCode(String code);

	List<Class> findByCourse(Course course);

	List<Class> findByDiscipline(Discipline discipline);
}
