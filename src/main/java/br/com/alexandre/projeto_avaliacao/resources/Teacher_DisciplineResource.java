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

import br.com.alexandre.projeto_avaliacao.domain.Teacher_Discipline;
import br.com.alexandre.projeto_avaliacao.domain.dto.Teacher_DisciplineDTO;
import br.com.alexandre.projeto_avaliacao.services.DisciplineService;
import br.com.alexandre.projeto_avaliacao.services.TeacherService;
import br.com.alexandre.projeto_avaliacao.services.Teacher_DisciplineService;

@RestController
@RequestMapping(value = "/teachers_disciplines")
public class Teacher_DisciplineResource {

	@Autowired
	Teacher_DisciplineService service;

	@Autowired
	TeacherService teacherService;

	@Autowired
	DisciplineService disciplineService;

	@PostMapping
	public ResponseEntity<Teacher_DisciplineDTO> insert(@RequestBody Teacher_DisciplineDTO dto) {
		return ResponseEntity.ok(service.save(new Teacher_Discipline(dto, teacherService.findById(dto.getIdTeacher()),
				disciplineService.findById(dto.getIdDiscipline()))).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<Teacher_DisciplineDTO>> listAll() {
		return ResponseEntity
				.ok(service.listAll().stream().map((teacher_Discipline) -> teacher_Discipline.toDTO()).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Teacher_DisciplineDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Teacher_DisciplineDTO> update(@PathVariable Integer id,
			@RequestBody Teacher_DisciplineDTO dto) {
		return ResponseEntity.ok(service.save(new Teacher_Discipline(dto, teacherService.findById(dto.getIdTeacher()),
				disciplineService.findById(dto.getIdDiscipline()))).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<List<Teacher_DisciplineDTO>> findByTeacher(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByTeacher(teacherService.findById(id)).stream()
				.map((teacher_Discipline) -> teacher_Discipline.toDTO()).toList());
	}
	
	@GetMapping(".disciplines/{id}")
	public ResponseEntity<List<Teacher_DisciplineDTO>> findByDiscipline(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByDiscipline(disciplineService.findById(id)).stream()
				.map((teacher_Discipline) -> teacher_Discipline.toDTO()).toList());
	}
}
