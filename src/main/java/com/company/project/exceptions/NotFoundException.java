package com.company.project.exceptions;

public class NotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super("Object not found!");
	}
}