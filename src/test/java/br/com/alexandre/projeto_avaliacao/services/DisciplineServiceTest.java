package br.com.alexandre.projeto_avaliacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.BaseTests;
import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class DisciplineServiceTest extends BaseTests {

	@Autowired
	DisciplineService service;

	@Autowired
	CourseService courseService;

	@Test
	@DisplayName("Test insert discipline")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insert() {
		var discipline = service.save(new Discipline(null, "Test", courseService.findById(3)));
		assertEquals("Ciência da Computação", discipline.getCourse().getName());
	}

	@Test
	@DisplayName("Test insert discipline com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insertWithNameNull() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.save(new Discipline(null, null, courseService.findById(3))));
		assertEquals("O nome da disciplina não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert discipline com curso nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insertWithCourseNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Discipline(null, "Teste", null)));
		assertEquals("A disciplina precisa estar vinculada a um curso", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert disciplina com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insertWithCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.save(new Discipline(null, "teste", courseService.findById(30))));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update disciplina")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void update() {
		var discipline = new Discipline(3, "Analise de dados", courseService.findById(3));
		service.update(discipline);
		assertEquals("Analise de dados", discipline.getName());
	}

	@Test
	@DisplayName("Test update disciplina inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void updateUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new Discipline(30, "Disciplina", courseService.findById(3))));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update disciplina com curso nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void updateCourseNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Discipline(3, "Disciplina", null)));
		assertEquals("A disciplina precisa estar vinculada a um curso", exception.getMessage());
	}

	@Test
	@DisplayName("Test update disciplina com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void updateCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new Discipline(3, "Disciplina", courseService.findById(30))));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update disciplina com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void updateWithNameNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Discipline(3, null, courseService.findById(3))));
		assertEquals("O nome da disciplina não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test lista todos")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void listAll() {
		List<Discipline> list = service.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Test deleta")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void delete() {
		service.delete(3);
		List<Discipline> list = service.listAll();
		assertEquals(2, list.size());
		assertEquals("Desenvolvimento web e mobile", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Test deleta id inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void deleteIdUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("A disciplina 10 não existe", exception.getMessage());
		List<Discipline> list = service.listAll();
		assertEquals(3, list.size());
	}
	@Test
	@DisplayName("Test buscar por nome")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void findByName() {
		var discipline = service.findByNameContainsIgnoreCase("Modelagem");
		assertEquals(1, discipline.size());
	}
	
	@Test
	@DisplayName("Test buscar por nome inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void findByNameUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainsIgnoreCase("erro"));
		assertEquals("Nenhuma disciplina encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Test buscar por curso")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void findByCourse() {
		var disciplines = service.findByCourse(courseService.findById(3));
		assertEquals(2, disciplines.size());
	}
	
	@Test
	@DisplayName("Test buscar por curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void findByCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCourse(courseService.findById(30)));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test buscar por curso sem disciplina")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	public void findByCourseWithoutDiscipline() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCourse(courseService.findById(4)));
		assertEquals("Nenhuma disciplina cadastrada para esse curso", exception.getMessage());
	}
}
