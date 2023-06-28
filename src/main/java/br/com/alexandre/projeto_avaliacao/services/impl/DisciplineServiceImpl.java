package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.repositories.DisciplineRepository;
import br.com.alexandre.projeto_avaliacao.services.DisciplineService;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;

@Service
public class DisciplineServiceImpl implements DisciplineService {

	@Autowired
	DisciplineRepository repository;

	private void validate(Discipline discipline) {
		if (discipline.getName() == null || discipline.getCourse() == null) {
			throw new IntegrityViolation("Os valores não podem ser nulos, preencha-os novamente");
		}
	}

	@Override
	public Discipline save(Discipline discipline) {
		validate(discipline);
		return repository.save(discipline);
	}

	@Override
	public Discipline update(Discipline discipline) {
		Discipline altDiscipline = findById(discipline.getId());
		validate(discipline);
		return repository.save(altDiscipline);
	}

	@Override
	public void delete(Integer id) {
		Discipline discipline = findById(id);
		repository.delete(discipline);
	}

	@Override
	public Discipline findById(Integer id) {
		Optional<Discipline> discipline = repository.findById(id);
		return discipline.orElseThrow(() -> new ObjectNotFound("A disciplina %s não existe".formatted(id)));
	}

	@Override
	public List<Discipline> listAll() {
		return repository.findAll();
	}

	@Override
	public List<Discipline> findByNameContainsIgnoreCase(String nome) {
		return repository.findByNameContainsIgnoreCase(nome);
	}

	@Override
	public List<Discipline> FindByCourse(Course course) {
		List<Discipline> list = repository.findByCourse(course);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum curso cadastrado para essa disciplina");
		}
		return list;
	}

}
