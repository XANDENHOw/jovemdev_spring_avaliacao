package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.repositories.StudyClassRepository;
import br.com.alexandre.projeto_avaliacao.services.StudyClassService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class StudyClassServiceImpl implements StudyClassService {

	@Autowired
	StudyClassRepository repository;

	private void validate(StudyClass studyClass) {
		if (studyClass.getCode() == null) {
			throw new IntegrityViolation("Para cadastrar uma turma, o código não pode ser nulo");
		}
		if (studyClass.getDiscipline() == null) {
			throw new IntegrityViolation("Para cadastrar uma turma, a disciplina não pode ser nula");
		}
		if(studyClass.getCourse() == null) {
			throw new IntegrityViolation("Para cadastrar uma turma, o curso não pode ser nulo");

		}
	}

	@Override
	public StudyClass save(StudyClass studyClass) {
		validate(studyClass);
		return null;
	}

	@Override
	public StudyClass update(StudyClass studyClass) {
		StudyClass altClass = findById(studyClass.getId());
		validate(studyClass);
		return repository.save(altClass);
	}

	@Override
	public void delete(Integer id) {
		StudyClass delClass = findById(id);
		repository.delete(delClass);
	}

	@Override
	public StudyClass findById(Integer id) {
		Optional<StudyClass> studyClass = repository.findById(id);
		return studyClass.orElseThrow(() -> new ObjectNotFound("A turma %s não existe".formatted(id)));
	}

	@Override
	public List<StudyClass> listAll() {
		return repository.findAll();
	}

	@Override
	public StudyClass findByCode(String code) {
		Optional<StudyClass> stdClass = repository.findByCode(code);
		return stdClass.orElseThrow(() -> new ObjectNotFound("Turma %s não encontrada!".formatted(code)));
	}

	@Override
	public List<StudyClass> findByCourse(Course course) {
		List<StudyClass> list = repository.findByCourse(course);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhuma turma cadastrada para esse curso");
		}
		return list;
	}

	@Override
	public List<StudyClass> findByDiscipline(Discipline discipline) {
		List<StudyClass> list = repository.findByDiscipline(discipline);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhuma turma cadastrada para essa disciplina");
		}
		return list;
	}

}