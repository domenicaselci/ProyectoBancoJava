package Excepciones;

import java.io.IOException;

public class SaldoNegativoException extends IOException{
	
	public SaldoNegativoException(){
		
	}
	
	public String getMessage() {
		return "Saldo Negativo";
	}
}
