package com.company.project.exceptions;

public class DuplicateCpfException extends Exception{

	private static final long serialVersionUID = 1L;

	public DuplicateCpfException() {
		super("Cpf registered!");
	}
}