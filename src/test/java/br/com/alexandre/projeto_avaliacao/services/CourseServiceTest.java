package br.com.alexandre.projeto_avaliacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.BaseTests;
import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CourseServiceTest extends BaseTests {

	@Autowired
	CourseService service;

	@Test
	@DisplayName("Test insert course")
	public void insert() {
		var course = service.save(new Course(null, "curso1"));
		assertEquals("curso1", course.getName());
	}

	@Test
	@DisplayName("Test insert course com nome já existente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insertWithNameUnavalaible() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Course(null, "Ciência da Computação")));
		assertEquals("Já existe um curso com esse nome", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test insert course com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insertWithNameNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Course(null, null)));
		assertEquals("O nome do curso não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por Id")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findById() {
		var course = service.findById(3);
		assertEquals("Ciência da Computação", course.getName());
	}
	
	@Test
	@DisplayName("Test buscar por Id inexitente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findByIdUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(20));
		assertEquals("O curso 20 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test deletar")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void delete() {
		service.delete(3);
		List<Course> list = service.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Test deletar Id inexitente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void deleteIdUnexist() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(20));
		assertEquals("O curso 20 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update course")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void update() {
		var course = new Course(3, "curso1");
		service.update(course);
		assertEquals("curso1", course.getName());
	}

	@Test
	@DisplayName("Test update course com nome já existente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void updateWithNameUnavalaible() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Course(4, "Ciência da Computação")));
		assertEquals("Já existe um curso com esse nome", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update course com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void updateWithNameNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Course(3, null)));
		assertEquals("O nome do curso não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por nome")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findByName() {
		var course = service.findByName("Ciência da Computação");
		assertEquals(3, course.getId());
	}

	@Test
	@DisplayName("Test buscar por nome inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findByNameUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByName("erro"));
		assertEquals("Curso erro não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar todos")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void listAll() {
		List<Course> list = service.listAll();
		assertEquals(3, list.size());
	}
}
