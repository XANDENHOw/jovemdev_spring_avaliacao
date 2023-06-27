package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {

	List<Class> findByCourse(Course course);

	List<Class> findByDiscipline(Discipline discipline);

	Class findByCode(String code);

}
