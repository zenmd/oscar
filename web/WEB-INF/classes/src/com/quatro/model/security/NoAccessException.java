package com.quatro.model.security;

public class NoAccessException extends Exception{
	private static final long serialVersionUID = -4444364883235024698L;

	public NoAccessException(String s){
		super(s);
	}
	public NoAccessException()
	{
		super("No enough privilege to access the requested page.");
	}
}
