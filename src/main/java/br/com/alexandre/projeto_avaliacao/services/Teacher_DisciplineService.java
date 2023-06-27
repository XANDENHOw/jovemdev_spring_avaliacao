package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.domain.Teacher_Discipline;

public interface Teacher_DisciplineService {

	Teacher_Discipline save(Teacher_Discipline teacher_Discipline);

	Teacher_Discipline update(Teacher_Discipline teacher_Discipline);

	Teacher_Discipline findById(Integer id);

	void delete(Integer id);

	List<Teacher_Discipline> listAll();

	List<Teacher_Discipline> findByTeacher(Teacher teacher);

	List<Teacher_Discipline> findByDiscipline(Discipline discipline);
}
