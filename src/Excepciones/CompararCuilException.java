package Excepciones;

import java.io.IOException;

public class CompararCuilException extends IOException {

	public CompararCuilException() {
		
	}
	
	public String getMessage() {
		return "Cuil Incorrecto";
	}
}
