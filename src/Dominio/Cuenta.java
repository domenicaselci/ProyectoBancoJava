package Dominio;

import java.time.LocalDate;

import Excepciones.CompararCuotasException;
import Excepciones.SaldoNegativoException;


public class Cuenta {
	private int  Cuenta;
	private int Cbu;
	private Cliente Dni;
	private LocalDate FechadeCreacion;
	private TipoDeCuenta TipoDeCuenta;
	private float Saldo;
	private Boolean Estado;
	
			
	public Cuenta(int cuenta, int cbu, Cliente dni, LocalDate fechadeCreacion,TipoDeCuenta tipoDeCuenta, float saldo,Boolean estado) {
		
		Cuenta = cuenta;
		Cbu = cbu;
		Dni = dni;
		FechadeCreacion = fechadeCreacion;
		TipoDeCuenta = tipoDeCuenta;
		Saldo = saldo;
		Estado = estado;
	}
			  
	public Cuenta() {}
			  
	public int getCuenta() {
		return Cuenta;
	}
	public void setCuenta(int cuenta) {
		Cuenta = cuenta;
	}
	public int getCbu() {
		return Cbu;
	}
	public void setCbu(int cbu) {
		Cbu = cbu;
	}
	public Cliente getDni() {
		return Dni;
	}
	public void setDni(Cliente dni) {
		Dni = dni;
	}
	public LocalDate getFechadeCreacion() {
		return FechadeCreacion;
	}
	public void setFechadeCreacion(LocalDate fechadeCreacion) {
		FechadeCreacion = fechadeCreacion;
	}
	public TipoDeCuenta getTipoDeCuenta() {
		return TipoDeCuenta;
	}
	public void setTipoDeCuenta(TipoDeCuenta tipoDeCuenta) {
		TipoDeCuenta = tipoDeCuenta;
	}
	public float getSaldo() {
		return Saldo;
	}
	public void setSaldo(float saldo) {
		Saldo = saldo;
	}
	public boolean isEstado() {
		return Estado;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}

	public String toString() {
		return "Cuenta Cbu: " + Cbu + ", Dni: " + Dni + ", FechadeCreacion: " + FechadeCreacion + ", TipoDeCuenta: "
			+ TipoDeCuenta + ", Saldo: " + Saldo + ", Estado: " + Estado + ", Cuenta: " + Cuenta + "";
	}
	
	public static boolean saldoNegativo(Prestamos pre,Cuenta cu) throws SaldoNegativoException {
		Boolean menor = false;
		
		if(pre.getMontoCuotas()>cu.getSaldo()) {
			menor = true;
		}
		
		if(menor) {
			throw new SaldoNegativoException();
		}
		return menor;
	}
}
