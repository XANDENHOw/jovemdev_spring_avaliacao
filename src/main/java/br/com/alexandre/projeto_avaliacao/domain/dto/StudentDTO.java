package br.com.alexandre.projeto_avaliacao.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentDTO {
	
	private Integer registration;
	private String name;
	private String birth;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String phone;
	private Integer idClass;
	private String classCode;
	
}
