package br.com.alexandre.projeto_avaliacao.domain;

import java.time.LocalDate;

import br.com.alexandre.projeto_avaliacao.domain.dto.TeacherDTO;
import br.com.alexandre.projeto_avaliacao.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "teacher")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_teacher")
	@Setter
	private Integer id;

	@Column(name = "name_teacher")
	private String name;

	@Column(name = "birth_teacher")
	private LocalDate birth;

	@Column(name = "email_teacher", unique = true)
	private String email;

	@Column(name = "password_teacher")
	private String password;

	@Column(name = "qualification_teacher")
	private String qualification;

	@Column(name = "phone_teacher")
	private String phone;

	public Teacher(TeacherDTO dto) {
		this(dto.getId(), dto.getName(), DateUtils.strToLocalDate(dto.getBirth()), dto.getEmail(), dto.getPassword(),
				dto.getQualification(), dto.getPhone());
	}

	public TeacherDTO toDTO() {
		return new TeacherDTO(this.id, this.name, DateUtils.LocalDateToStr(birth), this.email, this.password,
				this.qualification, this.phone);
	}
}
