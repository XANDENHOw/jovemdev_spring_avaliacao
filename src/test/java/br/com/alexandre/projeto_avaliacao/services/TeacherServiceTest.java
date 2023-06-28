package br.com.alexandre.projeto_avaliacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.BaseTests;
import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import br.com.alexandre.projeto_avaliacao.utils.DateUtils;
import jakarta.transaction.Transactional;

@Transactional
public class TeacherServiceTest extends BaseTests {

	@Autowired
	TeacherService service;

	@Test
	@DisplayName("Test insert teacher")
	public void insert() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var teacher = new Teacher(null, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766");
		service.save(teacher);
		assertEquals("prof2", teacher.getName());
	}

	@Test
	@DisplayName("Test find teacher by id")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findById() {
		var teacher = service.findById(3);
		assertEquals("prof1", teacher.getName());
	}

	@Test
	@DisplayName("Test find teacher by id inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByIdUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Professor 10 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Test delete teacher")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void delete() {
		service.delete(3);
		List<Teacher> list = service.listAll();
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("Test delete teacher inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void deleteUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Professor 10 não encontrado!", exception.getMessage());
		List<Teacher> list = service.listAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Test update teacher")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void update() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var teacher = new Teacher(3, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766");
		service.update(teacher);
		assertEquals("prof2", teacher.getName());
	}

	@Test
	@DisplayName("Test update teacher inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateUnExistent() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new Teacher(30, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766")));
		assertEquals("Professor 30 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com email já cadastrado")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateEmailEquals() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Teacher(3, "prof2", birth, "email12@test.com", "senha2", "doutor", "999887766")));
		assertEquals("Esse email já está sendo utilizado", exception.getMessage());
	}
}
