package br.com.alexandre.projeto_avaliacao.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.domain.Student;
import br.com.alexandre.projeto_avaliacao.repositories.StudentRepository;
import br.com.alexandre.projeto_avaliacao.services.StudentService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository repository;

	private void validate(Student student) {
		if (student.getName() == null || student.getEmail() == null
				|| student.getPassword() == null || student.getPhone() == null || student.getBirth() == null
				|| student.getStudyClass() == null) {
			throw new IntegrityViolation("Os dados do aluno não podem ser nulos");
		}
	}

	@Override
	public Student save(Student student) {
		validate(student);
		return repository.save(student);
	}

	@Override
	public Student update(Student student) {
		Student altStudent = findByResgistration(student.getRegistration());
		validate(student);
		return repository.save(altStudent);
	}

	@Override
	public void delete(Integer registration) {
		Student altStudent = findByResgistration(registration);
		repository.delete(altStudent);
	}

	@Override
	public Student findByResgistration(Integer registration) {
		Optional<Student> student = repository.findByRegistration(registration);
		return student
				.orElseThrow(() -> new ObjectNotFound("Não existem alunos com essa matrícula".formatted(registration)));
	}

	@Override
	public List<Student> listAll() {
		return repository.findAll();
	}

	@Override
	public List<Student> findByName(String name) {
		return repository.findByNameContainsIgnoreCase(name);
	}

	@Override
	public List<Student> findByBirthYear(LocalDate birth) {
		return repository.findByBirth(birth);
	}

	@Override
	public List<Student> findByClass(StudyClass studyClass) {
		List<Student> list = repository.findByStudyClass(studyClass);
		if(list.size() == 0) {
			new ObjectNotFound("Não existem alunos matriculados nessa turma");
		}
		return list;
	}

	@Override
	public Student findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
