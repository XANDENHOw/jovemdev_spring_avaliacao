package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Course;

public interface CourseService {

	Course save(Course course);

	Course update(Course course);

	void delete(Integer id);

	Course findById(Integer id);

	List<Course> listAll();

	Course findByName(String name);

}
