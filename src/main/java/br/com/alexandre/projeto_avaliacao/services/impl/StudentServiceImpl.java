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
		if (student.getName() == null) {
			throw new IntegrityViolation("O nome do aluno não pode ser nulo");
		}
		if (student.getBirth() == null) {
			throw new IntegrityViolation("O aluno precisa de uma data de nascimento(dd/MM/yyyy)");
		}
		if (student.getEmail() == null) {
			throw new IntegrityViolation("O email não pode ser nulo");

		}
		if (student.getPassword() == null) {
			throw new IntegrityViolation("A senha não pode ser nula");
		}
		if (student.getPhone() == null) {
			throw new IntegrityViolation("O telefone de contato não pode ser nulo");
		}
		if (student.getStudyClass() == null) {
			throw new IntegrityViolation("O aluno precisa estar vinculado a uma turma");
		}
	}
	
	private void validateEmail(Student student) {
		Optional<Student> optStudent = repository.findByEmail(student.getEmail());
		if(optStudent.isPresent()) {
			Student validaStudent = optStudent.get();
			if(student.getRegistration() != validaStudent.getRegistration()) {
				throw new IntegrityViolation("Esse email já está sendo utilizado");
			}
		}
	}

	@Override
	public Student save(Student student) {
		validate(student);
		validateEmail(student);
		return repository.save(student);
	}

	@Override
	public Student update(Student student) {
		Student altStudent = findByResgistration(student.getRegistration());
		validate(student);
		validateEmail(student);
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
		List<Student> list = repository.findByNameContainsIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum aluno encontrado");
		}
		return list;
	}

	@Override
	public List<Student> findByBirthYear(LocalDate birth) {
		List<Student> list = repository.findByBirth(birth);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum aluno nasceu nessa data");
		}
		return list;
	}

	@Override
	public List<Student> findByClass(StudyClass studyClass) {
		List<Student> list = repository.findByStudyClass(studyClass);
		if(list.size() == 0) {
			throw new ObjectNotFound("Não existem alunos matriculados nessa turma");
		}
		return list;
	}

	@Override
	public Student findByEmail(String email) {
		Optional<Student> student =  repository.findByEmail(email);
		return student.orElseThrow(() -> new ObjectNotFound("Email %s não encontrado!".formatted(email)));
	}

}
