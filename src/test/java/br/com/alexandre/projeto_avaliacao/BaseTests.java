package br.com.alexandre.projeto_avaliacao;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.alexandre.projeto_avaliacao.services.CourseService;
import br.com.alexandre.projeto_avaliacao.services.DisciplineService;
import br.com.alexandre.projeto_avaliacao.services.StudentService;
import br.com.alexandre.projeto_avaliacao.services.StudyClassService;
import br.com.alexandre.projeto_avaliacao.services.TeacherService;
import br.com.alexandre.projeto_avaliacao.services.Teacher_DisciplineService;
import br.com.alexandre.projeto_avaliacao.services.impl.CourseServiceImpl;
import br.com.alexandre.projeto_avaliacao.services.impl.DisciplineServiceImpl;
import br.com.alexandre.projeto_avaliacao.services.impl.StudentServiceImpl;
import br.com.alexandre.projeto_avaliacao.services.impl.StudyClassServiceImpl;
import br.com.alexandre.projeto_avaliacao.services.impl.TeacherServiceImpl;
import br.com.alexandre.projeto_avaliacao.services.impl.Teacher_DisciplineServiceImpl;


@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public CourseService courseService() {
		return new CourseServiceImpl();
	}
	
	@Bean
	public DisciplineService disciplineService() {
		return new DisciplineServiceImpl();
	}
	
	@Bean
	public StudentService studentService() {
		return new StudentServiceImpl();
	}
	
	@Bean
	public StudyClassService studyClassService() {
		return new StudyClassServiceImpl();
	}
	
	@Bean
	public Teacher_DisciplineService teacher_DisciplineService() {
		return new Teacher_DisciplineServiceImpl();
	}
	
	@Bean
	public TeacherService teacherService() {
		return new TeacherServiceImpl();
	}
}
