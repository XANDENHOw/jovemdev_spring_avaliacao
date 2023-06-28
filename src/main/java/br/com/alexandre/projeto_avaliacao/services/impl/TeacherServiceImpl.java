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
	
	private void validateEmail(Teacher teacher) {
		Optional<Teacher> optTeacher = repository.findByEmail(teacher.getEmail());
		if(optTeacher.isPresent()) {
			Teacher validaTeacher = optTeacher.get();
			if(teacher.getId() != validaTeacher.getId()) {
				throw new IntegrityViolation("Esse email já está sendo utilizado");
			}
			
		}
	}

	@Override
	public Teacher save(Teacher teacher) {
		validateTeacher(teacher);
		validateEmail(teacher);
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
		validateEmail(teacher);
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
		Optional<Teacher> teacher = repository.findByEmail(email);
		return teacher.orElseThrow(() -> new ObjectNotFound("Email %s não encontrado!".formatted(email)));
	}

	@Override
	public Teacher findById(Integer id) {
		Optional<Teacher> teacher = repository.findById(id);
		return teacher.orElseThrow(() -> new ObjectNotFound("Professor %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Teacher> findByBirthYear(LocalDate birth) {
		return repository.findByBirth(birth);
	}

}
