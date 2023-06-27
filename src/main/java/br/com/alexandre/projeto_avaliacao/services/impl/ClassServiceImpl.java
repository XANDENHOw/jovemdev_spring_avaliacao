package br.com.alexandre.projeto_avaliacao.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.services.ClassService;

@Service
public class ClassServiceImpl implements ClassService{

	@Override
	public Class save(Class studyClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class update(Class studyClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Class> findByCode(String code) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Class> findByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class> findByDiscipline(Discipline discipline) {
		// TODO Auto-generated method stub
		return null;
	}

}
