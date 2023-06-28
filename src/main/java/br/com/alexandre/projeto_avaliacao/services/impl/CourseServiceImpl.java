package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.repositories.CourseRepository;
import br.com.alexandre.projeto_avaliacao.services.CourseService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository repository;

	private void validate(Course course) {
		if (course.getName() == null) {
			throw new IntegrityViolation("O nome do curso não pode ser nulo");
		}
	}

	@Override
	public Course save(Course course) {
		validate(course);
		return repository.save(course);
	}

	@Override
	public Course update(Course course) {
		Course altCourse = findById(course.getId());
		validate(course);
		return repository.save(altCourse);
	}

	@Override
	public void delete(Integer id) {
		Course course = findById(id);
		repository.delete(course);
	}

	@Override
	public Course findById(Integer id) {
		Optional<Course> course = repository.findById(id);
		return course.orElseThrow(() -> new ObjectNotFound("O curso %s não existe".formatted(id)));
	}

	@Override
	public List<Course> listAll() {
		return repository.findAll();
	}

	@Override
	public Course findByName(String name) {
		return repository.findByName(name);
	}

}
