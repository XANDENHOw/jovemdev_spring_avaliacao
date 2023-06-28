package br.com.alexandre.projeto_avaliacao.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.repositories.TeacherRepository;
import br.com.alexandre.projeto_avaliacao.services.TeacherService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	TeacherRepository repository;

	private void validateTeacher(Teacher teacher) {
		if (teacher.getName() == null || teacher.getBirth() == null
				|| teacher.getEmail() == null || teacher.getPassword() == null || teacher.getQualification() == null
				|| teacher.getPhone() == null) {
			throw new IntegrityViolation("Você precisa informar todos os dados");
		}
	}

	@Override
	public Teacher save(Teacher teacher) {
		validateTeacher(teacher);
		return repository.save(teacher);
	}

	@Override
	public List<Teacher> listAll() {
		return repository.findAll();
	}

	@Override
	public Teacher update(Teacher teacher) {
		Teacher altTeacher = findById(teacher.getId());
		validateTeacher(teacher);
		return repository.save(altTeacher);
	}

	@Override
	public void delete(Integer id) {
		Teacher teacher = findById(id);
		repository.delete(teacher);
	}

	@Override
	public List<Teacher> findByNameContainsIgnoreCase(String name) {
		return repository.findByNameContainsIgnoreCase(name);
	}

	@Override
	public List<Teacher> findByQualification(String qualification) {
		return repository.findByQualification(qualification);
	}

	@Override
	public Teacher findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Teacher findById(Integer id) {
		Optional<Teacher> teacher = repository.findById(id);
		return teacher.orElseThrow(() -> new ObjectNotFound("Professor %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Teacher> findByBirthYear(LocalDate birth) {
		Integer year = birth.getYear();
		return repository.findByBirthYear(year);
	}

}
