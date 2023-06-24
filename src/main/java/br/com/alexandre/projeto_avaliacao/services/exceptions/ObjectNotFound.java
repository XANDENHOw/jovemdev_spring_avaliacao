package br.com.alexandre.projeto_avaliacao.services.exceptions;

public class ObjectNotFound extends RuntimeException{
	public ObjectNotFound(String message) {
		super(message);
	}
}
