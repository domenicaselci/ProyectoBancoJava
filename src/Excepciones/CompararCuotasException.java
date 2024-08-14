package Excepciones;

import java.io.IOException;

public class CompararCuotasException extends IOException{

	public CompararCuotasException() {
		
	}
	
	public String getMessage() {
		return "Cuotas Pagadas";
	}
}
