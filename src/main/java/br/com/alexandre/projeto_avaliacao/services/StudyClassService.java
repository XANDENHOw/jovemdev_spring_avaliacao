package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

public interface StudyClassService {

	StudyClass save(StudyClass studyClass);

	StudyClass update(StudyClass studyClass);

	void delete(Integer id);

	StudyClass findById(Integer id);

	List<StudyClass> listAll();

	StudyClass findByCode(String code);

	List<StudyClass> findByCourse(Course course);

	List<StudyClass> findByDiscipline(Discipline discipline);
}
