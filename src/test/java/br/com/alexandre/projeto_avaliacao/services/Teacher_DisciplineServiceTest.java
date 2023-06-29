package br.com.alexandre.projeto_avaliacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.BaseTests;
import br.com.alexandre.projeto_avaliacao.domain.Teacher_Discipline;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class Teacher_DisciplineServiceTest extends BaseTests{
	
	@Autowired
	Teacher_DisciplineService service;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	DisciplineService disciplineService;
	
	@Test
	@DisplayName("Test insert")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void insert() {
		var teaDisc = new Teacher_Discipline(6, teacherService.findById(3), disciplineService.findById(3));
		service.save(teaDisc);
		assertEquals("prof1", teaDisc.getTeacher().getName());
	}
	
	@Test
	@DisplayName("Test insert com professor nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void insertWithTeacherNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Teacher_Discipline(6, null, disciplineService.findById(3))));
		assertEquals("O professor não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert com professor inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void insertWithTeacherUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.save(new Teacher_Discipline(6, teacherService.findById(30), disciplineService.findById(3))));
		assertEquals("Professor 30 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert com disciplina nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void insertWithDisciplineNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Teacher_Discipline(6, teacherService.findById(3), null)));
		assertEquals("A disciplina não pode ser nula", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test insert com disciplina inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void insertWithDisciplineUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.save(new Teacher_Discipline(6, teacherService.findById(3), disciplineService.findById(30))));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Test update")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void update() {
		var teaDisc = new Teacher_Discipline(3, teacherService.findById(4), disciplineService.findById(3));
		service.update(teaDisc);
		assertEquals("prof2", teaDisc.getTeacher().getName());
	}

	@Test
	@DisplayName("Test update id inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void updateIdUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(new Teacher_Discipline(10, teacherService.findById(4), disciplineService.findById(3))));
		assertEquals("Nenhum professor foi vinculado a essa disciplina", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update com professor nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void updateWithTeacherNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Teacher_Discipline(3, null, disciplineService.findById(3))));
		assertEquals("O professor não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update com professor inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void updateWithTeacherUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(new Teacher_Discipline(3, teacherService.findById(30), disciplineService.findById(3))));
		assertEquals("Professor 30 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update com disciplina nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void updateWithDisciplineNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Teacher_Discipline(3, teacherService.findById(3), null)));
		assertEquals("A disciplina não pode ser nula", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update com disciplina inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/teacher_discipline.sql")
	public void updateWithDisciplineUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(new Teacher_Discipline(3, teacherService.findById(3), disciplineService.findById(30))));
		assertEquals("A disciplina 30 não existe", exception.getMessage());
	}
}
