package br.com.alexandre.projeto_avaliacao.domain;


import br.com.alexandre.projeto_avaliacao.domain.dto.PersonDTO;
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
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	@Setter
	private Integer id;
	
	@Column(name = "name_person")
	private String name;
	
	@Column(name = "email_person", unique = true)
	private String email;
	
	@Column(name = "password_person")
	private String password;
	
	@Column(name = "type_person")
	private String type;
	
	@ManyToOne
	private Phone phone;
	
	
	public Person (PersonDTO dto) {
		this(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getType(), new Phone(dto.getIdPhone(), null, null));
	}
	
	public PersonDTO toDTO() {
		return new PersonDTO(this.id, this.name, this.email, this.password, this.type, this.phone.getId());
	}
}
