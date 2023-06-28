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

import br.com.alexandre.projeto_avaliacao.domain.Discipline;
import br.com.alexandre.projeto_avaliacao.services.CourseService;
import br.com.alexandre.projeto_avaliacao.services.DisciplineService;

@RestController
@RequestMapping(value = "/disciplines")
public class DisciplineResource {

	@Autowired
	DisciplineService service;

	@Autowired
	CourseService courseService;

	@PostMapping
	public ResponseEntity<Discipline> insert(@RequestBody Discipline discipline) {
		return ResponseEntity.ok(service.save(discipline));
	}

	@GetMapping
	public ResponseEntity<List<Discipline>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((discipline) -> discipline).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Discipline> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Discipline> update(@PathVariable Integer id, @RequestBody Discipline discipline) {
		return ResponseEntity
				.ok(service.save(new Discipline(discipline.getId(), discipline.getName(), discipline.getCourse())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Discipline>> findByName(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameContainsIgnoreCase(name).stream().map((discipline) -> discipline).toList());
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<List<Discipline>> findByCourse(@PathVariable Integer id) {
		return ResponseEntity
				.ok(service.FindByCourse(courseService.findById(id)).stream().map((discipline) -> discipline).toList());
	}
}
