package br.com.alexandre.projeto_avaliacao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	List<Teacher> findByNameContainsIgnoreCase(String name);

	List<Teacher> findByQualification(String qualification);

	List<Teacher> findByBirth(LocalDate birth);

	Optional<Teacher> findByEmail(String email);
}
