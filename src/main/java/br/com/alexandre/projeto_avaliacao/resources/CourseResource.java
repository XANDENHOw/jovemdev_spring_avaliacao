package br.com.alexandre.projeto_avaliacao.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.services.CourseService;

@RestController
@RequestMapping(value = "/courses")
public class CourseResource {

	@Autowired
	CourseService service;

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<Course> insert(@RequestBody Course course) {
		return ResponseEntity.ok(service.save(course));
	}

	@Secured("ADMIN")
	@GetMapping
	public ResponseEntity<List<Course>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((course) -> course).toList());
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/{id}")
	public ResponseEntity<Course> update(@PathVariable Integer id, @RequestBody Course course) {
		return ResponseEntity.ok(service.save(new Course(course.getId(), course.getName())));
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/name/{name}")
	public ResponseEntity<Course> findByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByName(name));
	}
}
