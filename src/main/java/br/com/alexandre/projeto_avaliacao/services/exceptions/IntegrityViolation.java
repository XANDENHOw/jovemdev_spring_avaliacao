package br.com.alexandre.projeto_avaliacao.services.exceptions;

public class IntegrityViolation extends RuntimeException{
	public IntegrityViolation(String message) {
		super(message);
	}
}
