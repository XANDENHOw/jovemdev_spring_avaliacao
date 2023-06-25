package br.com.alexandre.projeto_avaliacao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher_DisciplineDTO {
	
	private Integer id;
	private Integer idTeacher;
	private String nameTeacher;
	private Integer idDiscipline;
	private String nameDiscipline;
	
}
