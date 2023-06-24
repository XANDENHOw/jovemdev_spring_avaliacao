package br.com.alexandre.projeto_avaliacao.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@Entity(name = "student_class")
public class Student_Class {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Class studyGroup;

}
