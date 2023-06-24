package br.com.alexandre.projeto_avaliacao.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teacher")
public class Teacher extends Person{
	
	@Column(name = "teacher_qualification")
	private String qualification;
	
}
