package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

	List<Teacher> findByNameContainsIgnoreCase(String name);
	Teacher findByEmail(String email);
	List<Teacher> findByQualification(String qualification);
	
}
