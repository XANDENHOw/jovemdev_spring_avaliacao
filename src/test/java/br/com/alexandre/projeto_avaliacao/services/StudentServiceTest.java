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
import br.com.alexandre.projeto_avaliacao.domain.Student;
import br.com.alexandre.projeto_avaliacao.services.exceptions.IntegrityViolation;
import br.com.alexandre.projeto_avaliacao.services.exceptions.ObjectNotFound;
import br.com.alexandre.projeto_avaliacao.utils.DateUtils;
import jakarta.transaction.Transactional;

@Transactional
public class StudentServiceTest extends BaseTests {

	@Autowired
	StudentService service;

	@Autowired
	StudyClassService studyClassService;

	@Autowired
	DisciplineService disciplineService;
	
	@Autowired
	CourseService courseService;

	@Test
	@DisplayName("Test insert student")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insert() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var student = new Student(null, "aluno1", birth, "aluno2@test.com", "senha2", "999887766",
				studyClassService.findById(3));
		service.save(student);
		assertEquals("aluno1", student.getName());
	}

	@Test
	@DisplayName("Test insert student com email já existente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void insertWithEmailExistent() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Student(null, "aluno2", birth,
				"email@aluno", "senha3", "834238472985", studyClassService.findById(3))));
		assertEquals("Esse email já está sendo utilizado", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert student com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithNameNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Student(null, null, birth,
				"email12@test.com", "senha3", "834238472985", studyClassService.findById(3))));
		assertEquals("O nome do aluno não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert student com data de nascimento nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithBirthNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Student(null, "teste", null,
				"email12@test.com", "senha3", "834238472985", studyClassService.findById(3))));
		assertEquals("O aluno precisa de uma data de nascimento(dd/MM/yyyy)", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert student com email nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithEmailNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(
				new Student(null, "teste", birth, null, "senha3", "834238472985", studyClassService.findById(3))));
		assertEquals("O email não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert student com senha nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithPasswordNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(new Student(null, "teste", birth,
				"email12@test.com", null, "834238472985", studyClassService.findById(3))));
		assertEquals("A senha não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test insert student com telefone nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	public void insertWithPhoneNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.save(
				new Student(null, "teste", birth, "email12@test.com", "senha", null, studyClassService.findById(3))));
		assertEquals("O telefone de contato não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test find student by id")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findById() {
		var student = service.findByResgistration(3);
		assertEquals("aluno1", student.getName());
	}

	@Test
	@DisplayName("Test find student by id inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByIdUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByResgistration(10));
		assertEquals("Não existem alunos com essa matrícula", exception.getMessage());
	}

	@Test
	@DisplayName("Test delete student")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void delete() {
		service.delete(3);
		List<Student> list = service.listAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Test delete student inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void deleteUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Não existem alunos com essa matrícula", exception.getMessage());
		List<Student> list = service.listAll();
		assertEquals(3, list.size());
	}

	@Test
	@DisplayName("Test update student")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void update() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var student = new Student(3, "alunoAlterado", birth, "alunoAlterado@test", "senha2", "999887766",
				studyClassService.findById(3));
		service.update(student);
		assertEquals("alunoAlterado", student.getName());
	}

	@Test
	@DisplayName("Test update student inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateUnExistent() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(new Student(30, "aluno2", birth,
				"aluno2@test.com", "senha2", "999887766", studyClassService.findById(3))));
		assertEquals("Não existem alunos com essa matrícula", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com email já cadastrado")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateEmailEquals() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(
				new Student(3, "aluno2", birth, "email@aluno2", "senha2", "999887766", studyClassService.findById(3))));
		assertEquals("Esse email já está sendo utilizado", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com nome nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateWithNameNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Student(3, null, birth,
				"email123@test.com", "senha2", "999887766", studyClassService.findById(3))));
		assertEquals("O nome do aluno não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com data de nascimento nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateWithBirthNull() {
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(new Student(3, "null", null,
				"email123@test.com", "senha2", "999887766", studyClassService.findById(3))));
		assertEquals("O aluno precisa de uma data de nascimento(dd/MM/yyyy)", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com email nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateEmailNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service
				.update(new Student(3, "aluno2", birth, null, "senha2", "999887766", studyClassService.findById(3))));
		assertEquals("O email não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com senha nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateWithPasswordNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(
				new Student(3, "null", birth, "email123@test.com", null, "999887766", studyClassService.findById(3))));
		assertEquals("A senha não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com telefone nulo")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateWithPhoneNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(
				new Student(3, "null", birth, "email123@test.com", "null", null, studyClassService.findById(3))));
		assertEquals("O telefone de contato não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Test update student com turma nula")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void updateWithStudyClassNull() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var exception = assertThrows(IntegrityViolation.class,
				() -> service.update(new Student(3, "null", birth, "email123@test.com", "null", "null", null)));
		assertEquals("O aluno precisa estar vinculado a uma turma", exception.getMessage());
	}

	@Test
	@DisplayName("Test buscar por email")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByEmail() {
		var student = service.findByEmail("email@aluno2");
		assertEquals("aluno2", student.getName());
	}

	@Test
	@DisplayName("Test buscar por email não existente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void FindByEmailUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByEmail("email@errado"));
		assertEquals("Email email@errado não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Test buscar por data de nascimento")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByBirthYear() {
		LocalDate birth = DateUtils.strToLocalDate("02/01/2000");
		var student = service.findByBirthYear(birth);
		assertEquals("aluno1", student.get(0).getName());
	}

	@Test
	@DisplayName("Test buscar por data de nascimento inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByBirthYearUnExistent() {
		LocalDate birth = DateUtils.strToLocalDate("10/12/2000");
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBirthYear(birth));
		assertEquals("Nenhum aluno nasceu nessa data", exception.getMessage());
	}

	@Test
	@DisplayName("Test buscar por nome")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByName() {
		var student = service.findByName("aluno");
		assertEquals(3, student.size());
	}

	@Test
	@DisplayName("Test buscar por nome inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByNameUnExistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByName("erro"));
		assertEquals("Nenhum aluno encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test buscar por turma")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByStudyClass() {
		var students = service.findByClass(studyClassService.findById(3));
		assertEquals(2, students.size());	
	}

	@Test
	@DisplayName("Test buscar por turma sem alunos")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByStudyClassWithoutStudents() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByClass(studyClassService.findById(5)));
		assertEquals("Não existem alunos matriculados nessa turma", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Test buscar por turma inexistente")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	@Sql(value = "classpath:/resources.sql/disciplines.sql")
	@Sql(value = "classpath:/resources.sql/studyclasses.sql")
	@Sql(value = "classpath:/resources.sql/students.sql")
	public void findByStudyClassUnexistent() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByClass(studyClassService.findById(30)));
		assertEquals("A turma 30 não existe", exception.getMessage());
	}
}
