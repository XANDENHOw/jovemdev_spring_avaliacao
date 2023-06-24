package br.com.alexandre.projeto_avaliacao.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class Student extends Person{
	
	@Column(name = "registration_student")
	private Integer idRegistration;
	
}
