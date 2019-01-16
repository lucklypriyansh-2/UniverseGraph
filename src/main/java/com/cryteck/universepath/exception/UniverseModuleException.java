package com.cryteck.universepath.exception;

public class UniverseModuleException extends RuntimeException {

	public UniverseModuleException(Exception e, String msg) {
		super(msg, e);
	}

}
