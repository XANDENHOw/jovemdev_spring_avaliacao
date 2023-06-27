package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.domain.Teacher_Discipline;

@Repository
public interface Teacher_DisciplineRepository extends JpaRepository<Teacher_Discipline, Integer> {

	List<Teacher_Discipline> findByTeacher(Teacher teacher);

	List<Teacher_Discipline> findByDiscipline(Discipline discipline);
}
