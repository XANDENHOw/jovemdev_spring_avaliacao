package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.domain.Teacher_Discipline;
import br.com.alexandre.projeto_avaliacao.repositories.Teacher_DisciplineRepository;
import br.com.alexandre.projeto_avaliacao.services.Teacher_DisciplineService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class Teacher_DisciplineServiceImpl implements Teacher_DisciplineService {

	@Autowired
	Teacher_DisciplineRepository repository;

	private void validate(Teacher_Discipline teacher_Discipline) {
		if (teacher_Discipline.getDiscipline() == null) {
			throw new IntegrityViolation("A disciplina não pode ser nula");
		}
		if (teacher_Discipline.getTeacher() == null) {
			throw new IntegrityViolation("O professor não pode ser nulo");
		}
	}

	@Override
	public Teacher_Discipline save(Teacher_Discipline teacher_Discipline) {
		validate(teacher_Discipline);
		return repository.save(teacher_Discipline);
	}

	@Override
	public Teacher_Discipline update(Teacher_Discipline teacher_Discipline) {
		Teacher_Discipline teacherDisc = findById(teacher_Discipline.getId());
		validate(teacher_Discipline);
		return repository.save(teacherDisc);
	}

	@Override
	public Teacher_Discipline findById(Integer id) {
		Optional<Teacher_Discipline> teaDisc = repository.findById(id);
		return teaDisc.orElseThrow(
				() -> new ObjectNotFound("Nenhum professor foi vinculado a essa disciplina".formatted(id)));
	}

	@Override
	public void delete(Integer id) {
		Teacher_Discipline teaDisc = findById(id);
		repository.delete(teaDisc);
	}

	@Override
	public List<Teacher_Discipline> listAll() {
		return repository.findAll();
	}

	@Override
	public List<Teacher_Discipline> findByTeacher(Teacher teacher) {
		List<Teacher_Discipline> list = repository.findByTeacher(teacher);
		if(list.size() == 0) {
			new ObjectNotFound("Não existem disciplinas cadastradas para esse professor");
		}
		return list;
	}

	@Override
	public List<Teacher_Discipline> findByDiscipline(Discipline discipline) {
		List<Teacher_Discipline> list = repository.findByDiscipline(discipline);
		if(list.size() == 0) {
			new ObjectNotFound("Não existem professores cadastrados para essa disciplina");
		}
		return list;
	}

}
