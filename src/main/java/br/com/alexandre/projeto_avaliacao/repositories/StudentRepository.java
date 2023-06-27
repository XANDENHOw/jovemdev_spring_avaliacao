package br.com.alexandre.projeto_avaliacao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	List<Student> findByNameContaisIgnoreCase(String nome);

	List<Student> findByBirthYear(Integer birth);

	List<Student> findByClass(Class classCode);

	Student findByEmail(String email);

	Optional<Student> findByRegistration(Integer registration);
}
