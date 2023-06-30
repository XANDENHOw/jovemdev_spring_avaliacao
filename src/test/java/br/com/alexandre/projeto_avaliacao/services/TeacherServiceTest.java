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
		var teacher = new Teacher(null, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766", "ADMIN,USER");
		service.save(teacher);
		assertEquals("prof2", teacher.getName());
	}

	@Test
	@DisplayName("Test insert teacher com email já existente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithEmailExistent() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new Teacher(null, "prof3", birth, "email1@test.com", "senha3", "mestre", "834238472985", "ADMIN,USER")));
		assertEquals("Esse email já está sendo utilizado", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com nome nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithNameNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new Teacher(null, null, birth, "email12@test.com", "senha3", "mestre", "834238472985", "ADMIN,USER")));
		assertEquals("O nome do professor não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com data de nascimento nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithBirthNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new Teacher(null, "teste", null, "email12@test.com", "senha3", "mestre", "834238472985", "ADMIN,USER")));
		assertEquals("O professor precisa de uma data de nascimento(dd/MM/yyyy)", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com email nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithEmailNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.save(new Teacher(null, "teste", birth, null, "senha3", "mestre", "834238472985", "ADMIN,USER")));
		assertEquals("O email não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com senha nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithPasswordNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new Teacher(null, "teste", birth, "email12@test.com", null, "mestre", "834238472985", "ADMIN,USER")));
		assertEquals("A senha não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com qualificação nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithQualificationNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.save(new Teacher(null, "teste", birth, "email12@test.com", "senha", null, "834238472985", "ADMIN,USER")));
		assertEquals("A qualificação do professor não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert teacher com telefone nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void insertWithPhoneNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.save(new Teacher(null, "teste", birth, "email12@test.com", "senha", "mestre", null, "ADMIN,USER")));
		assertEquals("O telefone de contato não pode ser nulo", exception.getMessage());
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
		var teacher = new Teacher(3, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766", "ADMIN,USER");
		service.update(teacher);
		assertEquals("prof2", teacher.getName());
	}

	@Test
	@DisplayName("Test update teacher inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateUnExistent() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(ObjectNotFound.class, () -> service
				.update(new Teacher(30, "prof2", birth, "prof2@test.com", "senha2", "doutor", "999887766", "ADMIN,USER")));
		assertEquals("Professor 30 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com email já cadastrado")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateEmailEquals() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Teacher(3, "prof2", birth, "email12@test.com", "senha2", "doutor", "999887766", "ADMIN,USER")));
		assertEquals("Esse email já está sendo utilizado", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com nome nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateWithNameNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Teacher(3, null, birth, "email123@test.com", "senha2", "doutor", "999887766", "ADMIN,USER")));
		assertEquals("O nome do professor não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com data de nascimento nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateWithBirthNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Teacher(3, "null", null, "email123@test.com", "senha2", "doutor", "999887766", "ADMIN,USER")));
		assertEquals("O professor precisa de uma data de nascimento(dd/MM/yyyy)", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com email nulo")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateEmailNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new Teacher(3, "prof2", birth, null, "senha2", "doutor", "999887766", "ADMIN,USER")));
		assertEquals("O email não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com senha nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateWithPasswordNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new Teacher(3, "null", birth, "email123@test.com", null, "doutor", "999887766", "ADMIN,USER")));
		assertEquals("A senha não pode ser nula", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test update teacher com qualificação nula")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateWithQualificationNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new Teacher(3, "null", birth, "email123@test.com", "null", null, "999887766", "ADMIN,USER")));
		assertEquals("A qualificação do professor não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test update teacher com telefone")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void updateWithPhoneNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new Teacher(3, "null", birth, "email123@test.com", "null", "null", null, "ADMIN,USER")));
		assertEquals("O telefone de contato não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por email")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByEmail() {
		var teacher = service.findByEmail("email12@test.com");
		assertEquals("prof2", teacher.getName());
	}
	
	@Test
	@DisplayName("Test buscar por email não existente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void FindByEmailUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByEmail("email@errado"));
		assertEquals("Email email@errado não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por data de nascimento")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByBirthYear() {
		LocalDate birth = DateUtils.strToLocalDate("01/01/1990");
		var teacher = service.findByBirthYear(birth);
		assertEquals("prof1", teacher.get(0).getName());
	}

	@Test
	@DisplayName("Test buscar por data de nascimento inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByBirthYearUnExistent() {
		LocalDate birth = DateUtils.strToLocalDate("01/01/1999");
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBirthYear(birth));
		assertEquals("Nenhum professor nasceu nessa data", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por nome")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByName() {
		var teacher = service.findByNameContainsIgnoreCase("prof");
		assertEquals(2, teacher.size());
	}

	@Test
	@DisplayName("Test buscar por nome inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByNameUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainsIgnoreCase("erro"));
		assertEquals("Nenhum professor encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por qualificação")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByQualification() {
		var teacher = service.findByQualification("mestre");
		assertEquals("prof1", teacher.get(0).getName());
	}
	
	@Test
	@DisplayName("Test buscar por qualificação inexistente")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	public void findByQualificationUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByQualification("erro"));
		assertEquals("Nenhum professor com essa formação foi encontrado", exception.getMessage());
	}
}
