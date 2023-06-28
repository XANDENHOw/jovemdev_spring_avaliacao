package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

@Repository
public interface StudyClassRepository extends JpaRepository<StudyClass, Integer> {

	List<StudyClass> findByCourse(Course course);

	List<StudyClass> findByDiscipline(Discipline discipline);

	StudyClass findByCode(String code);

}
