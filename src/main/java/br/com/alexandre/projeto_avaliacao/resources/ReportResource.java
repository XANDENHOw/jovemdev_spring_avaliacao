package br.com.alexandre.projeto_avaliacao.resources;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexandre.projeto_avaliacao.domain.Course;
import br.com.alexandre.projeto_avaliacao.domain.Student;
import br.com.alexandre.projeto_avaliacao.domain.dto.StudentCourseDTO;
import br.com.alexandre.projeto_avaliacao.domain.dto.StudentDTO;
import br.com.alexandre.projeto_avaliacao.services.CourseService;
import br.com.alexandre.projeto_avaliacao.services.StudentService;
import br.com.alexandre.projeto_avaliacao.services.StudyClassService;


@RestController
@RequestMapping("/reports")
public class ReportResource {

	@Autowired
	StudentService studentService;
	
	@Autowired
	StudyClassService studyClassService;
	
	@Autowired
	CourseService courseService;
	
	@GetMapping("/students-course/{idCourse}")
	public ResponseEntity<StudentCourseDTO> findStudentByCourse(@PathVariable Integer idCourse) {
		Course course = courseService.findById(idCourse);
		List<StudentDTO> list =  studyClassService.findByCourse(course).stream().flatMap(studyClass -> {
			try {
				return studentService.findByClass(studyClass).stream();
			}catch (ObjectNotFoundException e) {
				return Stream.empty();
			}
		}).map(Student::toDTO)
				.toList();;
		return ResponseEntity.ok(new StudentCourseDTO(list, course.getName()));
	}
}
