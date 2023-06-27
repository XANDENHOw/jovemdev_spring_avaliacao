package br.com.alexandre.projeto_avaliacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	Course findByName(String name);

}
