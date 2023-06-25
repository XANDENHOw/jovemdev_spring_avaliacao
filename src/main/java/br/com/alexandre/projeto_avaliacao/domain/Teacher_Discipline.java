package br.com.alexandre.projeto_avaliacao.domain;


import br.com.alexandre.projeto_avaliacao.domain.dto.Teacher_DisciplineDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "teacher_discipline")
public class Teacher_Discipline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_teacher_discipline")
	private Integer id;
	
	@ManyToOne
	private Teacher teacher;

	@ManyToOne
	private Discipline discipline;
	
	public Teacher_Discipline (Teacher_DisciplineDTO dto, Teacher teacher, Discipline discipline) {
		this(dto.getId(), teacher, discipline);
	}
	
	public Teacher_DisciplineDTO toDTO() {
		return new Teacher_DisciplineDTO(id, teacher.getId(), teacher.getName(), discipline.getId(), discipline.getName());
	}
}
