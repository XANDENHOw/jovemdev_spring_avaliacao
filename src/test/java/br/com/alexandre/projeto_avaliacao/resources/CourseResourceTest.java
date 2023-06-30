package br.com.alexandre.projeto_avaliacao.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.alexandre.projeto_avaliacao.ProjetoAvaliacaoApplication;
import br.com.alexandre.projeto_avaliacao.config.jwt.LoginDTO;
import br.com.alexandre.projeto_avaliacao.domain.Course;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjetoAvaliacaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getToken(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}

	private ResponseEntity<Course> getCourse(String url) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getToken("email1@test.com", "senha1")), Course.class);
	}

	private ResponseEntity<List<Course>> getCourses(String url) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getToken("email1@test.com", "senha1")), new ParameterizedTypeReference<List<Course>>() {
		});
	}

	@Test
	@DisplayName("Test insert curso com autenticação")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void insert() {
		Course course = new Course(null, "Como ser the greatest com Allioth");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(course, headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("Como ser the greatest com Allioth", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Buscar por nome")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findByNameTest() {
		ResponseEntity<List<Course>> response = getCourses("/courses/name/C");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(1, response.getBody().size());
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findByIdTest() {
		ResponseEntity<Course> response = getCourse("/courses/3");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Course course = response.getBody();
		assertEquals("Ciência da Computação", course.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void testGetNotFound() {
		ResponseEntity<Course> response = getCourse("/courses/100");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar curso")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void testCreateUser() {
		Course dto = new Course(null, "nome");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Course course = responseEntity.getBody();
		assertEquals("nome", course.getName());
	}

	@Test
	@DisplayName("Listar Todos")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void findAll() {
		ResponseEntity<List<Course>> response = rest.exchange("/courses", HttpMethod.GET,
				new HttpEntity<>(getToken("email1@test.com", "senha1")), new ParameterizedTypeReference<List<Course>>() {
				});
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses", HttpMethod.POST, requestEntity, Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(3, response.getBody().size());
	}

	@Test
	@DisplayName("Alterar curso")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void testUpdateUser() {
		Course dto = new Course(3, "nome");
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Course> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Course> responseEntity = rest.exchange("/courses/3", HttpMethod.PUT, requestEntity,
				Course.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Course user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}

	@Test
	@DisplayName("Excluir curso")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void testDeleteUser() {
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange("/courses/3", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Excluir curso inexistente")
	@Sql(value = "classpath:/resources.sql/clear_tables.sql")
	@Sql(value = "classpath:/resources.sql/inserts.sql")
	@Sql(value = "classpath:/resources.sql/courses.sql")
	public void testDeleteNonExistUser() {
		HttpHeaders headers = getToken("email1@test.com", "senha1");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange("/courses/100", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
