package br.com.alexandre.projeto_avaliacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.BaseTests;
import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class StudyClassServiceTest extends BaseTests {

	@Autowired
	StudyClassService service;

	@Autowired
	CourseService courseService;

	@Autowired
	DisciplineService disciplineService;

	@Test
	@DisplayName("Test insert turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insert() {
		var studyClass = new StudyClass(null, "turma1", courseService.findById(3), disciplineService.findById(4));
		service.save(studyClass);
		assertEquals("Ciência da Computação", studyClass.getCourse().getName());
	}

	@Test
	@DisplayName("Test insert turma com código nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithCodeNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new StudyClass(null, null, courseService.findById(3), disciplineService.findById(4))));
		assertEquals("Para cadastrar uma turma, o código não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert turma com curso nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithCourseNull() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.save(new StudyClass(null, "turma1", null, disciplineService.findById(4))));
		assertEquals("Para cadastrar uma turma, o curso não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert turma com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.save(new StudyClass(null, "turma1", courseService.findById(30), disciplineService.findById(4))));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert turma com disciplina nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithDisciplineNull() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.save(new StudyClass(null, "turma1", courseService.findById(3), null)));
		assertEquals("Para cadastrar uma turma, a disciplina não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert turma com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithDisciplineUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.save(new StudyClass(null, "turma1", courseService.findById(3), disciplineService.findById(30))));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void update() {
		var studyClass = new StudyClass(3, "turma1", courseService.findById(3), disciplineService.findById(4));
		service.update(studyClass);
		assertEquals("turma1", studyClass.getCode());
	}

	@Test
	@DisplayName("Test update turma id inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithIdUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new StudyClass(30, "turma1", courseService.findById(3), disciplineService.findById(4))));
		assertEquals("A turma 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma com código nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithCodeNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new StudyClass(3, null, courseService.findById(3), disciplineService.findById(4))));
		assertEquals("Para cadastrar uma turma, o código não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma com curso nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithCourseNull() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new StudyClass(3, "turma1", null, disciplineService.findById(4))));
		assertEquals("Para cadastrar uma turma, o curso não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new StudyClass(3, "turma1", courseService.findById(30), disciplineService.findById(4))));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma com disciplina nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithDisciplineNull() {
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new StudyClass(3, "turma1", courseService.findById(3), null)));
		assertEquals("Para cadastrar uma turma, a disciplina não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test update turma com curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void updateWithDisciplineUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new StudyClass(3, "turma1", courseService.findById(3), disciplineService.findById(30))));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test lista todas as turmas")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void listAll() {
		List<StudyClass> list = service.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Test deleta turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void delete() {
		service.delete(3);
		List<StudyClass> list = service.listAll();
		assertEquals(2, list.size());
		assertEquals("turma2", list.get(0).getCode());
	}
	
	@Test
	@DisplayName("Test deleta turma inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void deleteIdUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(20));
		assertEquals("A turma 20 não existe", exception.getMessage());		
	}
	
	@Test
	@DisplayName("Test busca por código")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByCode() {
		var stdClass = service.findByCode("turma1");
		assertEquals("Ciência da Computação", stdClass.getCourse().getName());
	}
	
	@Test
	@DisplayName("Test busca por código inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByCodeUnextent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCode("erro"));
		assertEquals("Turma erro não encontrada!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test busca por curso")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByCourse() {
		var stdClass = service.findByCourse(courseService.findById(3));
		assertEquals(2, stdClass.size());
	}
	
	@Test
	@DisplayName("Test busca por curso inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByCourseUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCourse(courseService.findById(30)));
		assertEquals("O curso 30 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test busca por curso sem turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByCourseWithoutClass() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCourse(courseService.findById(4)));
		assertEquals("Nenhuma turma cadastrada para esse curso", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test busca por disciplina")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByDiscipline() {
		var stdClass = service.findByDiscipline(disciplineService.findById(3));
		assertEquals(1, stdClass.size());
	}
	
	@Test
	@DisplayName("Test busca por disciplina inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByDisciplineUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDiscipline(disciplineService.findById(30)));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test busca por disciplina sem turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void findByDisciplineWithoutClass() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDiscipline(disciplineService.findById(5)));
		assertEquals("Nenhuma turma cadastrada para essa disciplina", exception.getMessage());
	}
}