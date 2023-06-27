package br.com.alexandre.projeto_avaliacao.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.domain.dto.TeacherDTO;
import br.com.alexandre.projeto_avaliacao.services.TeacherService;

@RestController
@RequestMapping("/teachers")
public class TeacherResource {

	@Autowired
	private TeacherService service;

	@PostMapping
	public ResponseEntity<TeacherDTO> insert(TeacherDTO dto) {
		Teacher teacher = service.save(new Teacher(dto));
		return ResponseEntity.ok(teacher.toDTO());
	}

	@GetMapping
	public ResponseEntity<List<TeacherDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((teacher) -> teacher.toDTO()).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TeacherDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TeacherDTO> update(@PathVariable Integer id, @RequestBody TeacherDTO dto) {
		return ResponseEntity.ok(service.update(new Teacher(dto)).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<TeacherDTO>> findByNameContainsIgnoreCase(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameContainsIgnoreCase(name).stream().map((teacher) -> teacher.toDTO()).toList());
	}

	@GetMapping("/qualification/{qualification}")
	public ResponseEntity<List<TeacherDTO>> findByQualification(@PathVariable String qualification) {
		return ResponseEntity
				.ok(service.findByQualification(qualification).stream().map((teacher) -> teacher.toDTO()).toList());
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<TeacherDTO> findByEmail(@PathVariable String email) {
		return ResponseEntity.ok(service.findByEmail(email).toDTO());
	}

}
