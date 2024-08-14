package Excepciones;

import java.io.IOException;

public class compararDniException extends IOException {
	public compararDniException() {
		
	}
	
	public String getMessage() {
		return "Dni Incorrecto";
	}
}
