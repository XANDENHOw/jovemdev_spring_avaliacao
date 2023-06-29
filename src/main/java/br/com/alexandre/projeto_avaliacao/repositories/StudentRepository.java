package br.com.alexandre.projeto_avaliacao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	List<Student> findByNameContainsIgnoreCase(String name);

	List<Student> findByBirth(LocalDate birth);

	List<Student> findByStudyClass(StudyClass studyClass);

	Optional<Student> findByEmail(String email);

	Optional<Student> findByRegistration(Integer registration);
}
