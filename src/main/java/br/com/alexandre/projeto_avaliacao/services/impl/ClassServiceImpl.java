package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.repositories.ClassRepository;
import br.com.alexandre.projeto_avaliacao.services.ClassService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository repository;

	private void validate(Class studyClass) {
		if (studyClass.getCode() == null || studyClass.getCourse() == null || studyClass.getDiscipline() == null) {
			throw new IntegrityViolation("Para cadastrar uma turma, os dados não podem ser nulos");
		}
	}

	@Override
	public Class save(Class studyClass) {
		validate(studyClass);
		return null;
	}

	@Override
	public Class update(Class studyClass) {
		Class altClass = findById(studyClass.getId());
		validate(studyClass);
		return repository.save(altClass);
	}

	@Override
	public void delete(Integer id) {
		Class delClass = findById(id);
		repository.delete(delClass);
	}

	@Override
	public Class findById(Integer id) {
		Optional<Class> studyClass = repository.findById(id);
		return studyClass.orElseThrow(() -> new ObjectNotFound("A turma %s não existe".formatted(id)));
	}

	@Override
	public List<Class> listAll() {
		return repository.findAll();
	}

	@Override
	public Class findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public List<Class> findByCourse(Course course) {
		List<Class> list = repository.findByCourse(course);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum curso cadastrado para essa turma");
		}
		return list;
	}

	@Override
	public List<Class> findByDiscipline(Discipline discipline) {
		List<Class> list = repository.findByDiscipline(discipline);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhuma disciplina cadastrada para essa turma");
		}
		return list;
	}

}
