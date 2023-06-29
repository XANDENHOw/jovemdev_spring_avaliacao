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
		if (teacher.getName() == null) {
			throw new IntegrityViolation("O nome do professor não pode ser nulo");
		}
		if (teacher.getBirth() == null) {
			throw new IntegrityViolation("O professor precisa de uma data de nascimento(dd/MM/yyyy)");
		}
		if (teacher.getEmail() == null) {
			throw new IntegrityViolation("O email não pode ser nulo");

		}
		if (teacher.getPassword() == null) {
			throw new IntegrityViolation("A senha não pode ser nula");
		}
		if (teacher.getQualification() == null) {
			throw new IntegrityViolation("A qualificação do professor não pode ser nula");
		}
		if (teacher.getPhone() == null) {
			throw new IntegrityViolation("O telefone de contato não pode ser nulo");
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
		List<Teacher> list = repository.findByNameContainsIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum professor encontrado");
		}
		return list;
	}

	@Override
	public List<Teacher> findByQualification(String qualification) {
		List<Teacher> list = repository.findByQualification(qualification);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum professor com essa formação foi encontrado");
		}
		return list;
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
		List<Teacher> list = repository.findByBirth(birth);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum professor nasceu nessa data");
		}
		return list;
	}

}
