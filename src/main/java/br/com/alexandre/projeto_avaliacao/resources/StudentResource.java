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

import br.com.alexandre.projeto_avaliacao.domain.Student;
import br.com.alexandre.projeto_avaliacao.domain.dto.StudentDTO;
import br.com.alexandre.projeto_avaliacao.services.StudyClassService;
import br.com.alexandre.projeto_avaliacao.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentResource {

	@Autowired
	StudentService service;

	@Autowired
	StudyClassService studyClassService;

	@PostMapping
	public ResponseEntity<StudentDTO> insert(@RequestBody StudentDTO dto) {
		return ResponseEntity.ok(service.save(new Student(dto, studyClassService.findById(dto.getIdClass()))).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<StudentDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((student) -> student.toDTO()).toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByResgistration(id).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StudentDTO> update(@PathVariable Integer id, @RequestBody StudentDTO dto) {
		return ResponseEntity.ok(service.save(new Student(dto, studyClassService.findById(dto.getIdClass()))).toDTO()); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<StudentDTO>> findByName(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByName(name).stream().map((student) -> student.toDTO()).toList());
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<StudentDTO> findByEmail(@PathVariable String email) {
		return ResponseEntity.ok(service.findByEmail(email).toDTO());
	}
	
	@GetMapping("/class/{id}")
	public ResponseEntity<List<StudentDTO>> findByClass(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByClass(studyClassService.findById(id)).stream()
				.map((student) -> student.toDTO()).toList());
	}

}
