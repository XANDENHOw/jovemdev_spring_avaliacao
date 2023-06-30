package br.com.alexandre.projeto_avaliacao.resources;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.alexandre.projeto_avaliacao.ProjetoAvaliacaoApplication;
import br.com.alexandre.projeto_avaliacao.config.jwt.LoginDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjetoAvaliacaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherResourceTest {

	@Autowired
	private TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
}
