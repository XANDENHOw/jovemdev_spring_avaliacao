package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

	List<Discipline> findByNameContainsIgnoreCase(String name);

	List<Discipline> findByCourse(Course course);

}
