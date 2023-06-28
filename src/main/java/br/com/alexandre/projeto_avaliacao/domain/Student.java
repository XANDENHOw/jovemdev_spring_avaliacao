package br.com.alexandre.projeto_avaliacao.domain;

import java.time.LocalDate;

import br.com.alexandre.projeto_avaliacao.domain.dto.StudentDTO;
import br.com.alexandre.projeto_avaliacao.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "registration")
@Entity(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_student")
	private Integer registration;

	@Column(name = "name_student")
	private String name;

	@Column(name = "birth_student")
	private LocalDate birth;

	@Column(name = "email_student", unique = true)
	private String email;

	@Column(name = "password_student")
	private String password;

	@Column(name = "phone_student")
	private String phone;

	@ManyToOne
	@JoinColumn(name = "class_student_id_class")
	private StudyClass studyClass;

	public Student(StudentDTO dto, StudyClass studyClass) {
		this(dto.getRegistration(), dto.getName(), DateUtils.strToLocalDate(dto.getBirth()), dto.getEmail(),
				dto.getPassword(), dto.getPhone(), studyClass);
	}

	public StudentDTO toDTO() {
		return new StudentDTO(this.registration, this.name, DateUtils.LocalDateToStr(birth), this.email, this.password,
				this.phone, this.studyClass.getId(), this.studyClass.getCode());
	}

}
