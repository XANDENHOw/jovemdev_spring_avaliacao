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

import br.com.alexandre.projeto_avaliacao.domain.StudyClass;
import br.com.alexandre.projeto_avaliacao.services.StudyClassService;
import br.com.alexandre.projeto_avaliacao.services.CourseService;
import br.com.alexandre.projeto_avaliacao.services.DisciplineService;

@RestController
@RequestMapping(value = "/studyclasses")
public class StudyClassResource {

	@Autowired
	StudyClassService service;

	@Autowired
	CourseService courseService;

	@Autowired
	DisciplineService disciplineService;

	@PostMapping
	public ResponseEntity<StudyClass> insert(@RequestBody StudyClass studyClass) {
		return ResponseEntity.ok(service.save(studyClass));
	}

	@GetMapping
	public ResponseEntity<List<StudyClass>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((studyClass) -> studyClass).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudyClass> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudyClass> update(@PathVariable Integer id, @RequestBody StudyClass studyClass) {
		return ResponseEntity.ok(service.save(new StudyClass(studyClass.getId(), studyClass.getCode(),
				studyClass.getCourse(), studyClass.getDiscipline())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/code/{code}")
	public ResponseEntity<StudyClass> findByCode(@PathVariable String code) {
		return ResponseEntity.ok(service.findByCode(code));
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<List<StudyClass>> findByCourse(@PathVariable Integer id) {
		return ResponseEntity
				.ok(service.findByCourse(courseService.findById(id)).stream().map((studyClass) -> studyClass).toList());
	}

	@GetMapping("/discipline/{id}")
	public ResponseEntity<List<StudyClass>> findByDiscipline(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByDiscipline(disciplineService.findById(id)).stream()
				.map((studyClass) -> studyClass).toList());
	}
}
