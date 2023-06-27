package br.com.alexandre.projeto_avaliacao.services;

import java.time.LocalDate;
import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Class;
import br.com.alexandre.projeto_avaliacao.domain.Student;

public interface StudentService {

	Student save(Student student);

	Student update(Student student);

	void delete(Integer id);

	Student findByResgistration(Integer registration);

	List<Student> listAll();

	List<Student> findByName(String name);

	List<Student> findByBirthYear(LocalDate birth);

	List<Student> findByClass(Class studyClass);

	Student findByEmail(String email);
}
