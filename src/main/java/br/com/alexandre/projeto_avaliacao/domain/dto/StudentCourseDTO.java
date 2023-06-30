package br.com.alexandre.projeto_avaliacao.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentCourseDTO {

	private List<StudentDTO> students;
	private String course;
}
