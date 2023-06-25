package br.com.alexandre.projeto_avaliacao.services.impl;

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
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	TeacherRepository repository;
	
	private void validatePerson(Teacher teacher) {
		if(teacher.getId() == null || teacher.getName() == null || teacher.getBirth() == null || teacher.getEmail() == null
				|| teacher.getPassword() == null || teacher.getQualification() == null || teacher.getPhone() == null) {
			throw new IntegrityViolation("Você precisa informar todos os dados");
		}
	}
	
	@Override
	public Teacher save(Teacher teacher) {
		validatePerson(teacher);
		return repository.save(teacher);
	}

	@Override
	public List<Teacher> listAll() {
		return repository.findAll();
	}

	@Override
	public Teacher update(Teacher teacher) {
		Teacher altTeacher = findById(teacher.getId());
		validatePerson(teacher);
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
		Optional<Teacher> person = repository.findById(id);
		return person.orElseThrow(() -> new ObjectNotFound("Pessoa %s não encontrado!".formatted(id)));
	}

}
