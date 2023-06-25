package br.com.alexandre.projeto_avaliacao.services;

import java.util.List;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;

public interface TeacherService {
	
	Teacher save(Teacher teacher);
	List<Teacher> listAll();
	Teacher update(Teacher teacher);
	void delete(Integer id);
	List<Teacher> findByNameContainsIgnoreCase(String name);
	List<Teacher> findByQualification(String qualification);
	Teacher findByEmail(String email);
	Teacher findById(Integer id);

}
