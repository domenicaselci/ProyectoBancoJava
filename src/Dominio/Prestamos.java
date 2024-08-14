package Dominio;

import java.time.LocalDate;

import Excepciones.CompararCuotasException;


public class Prestamos {

	private int Prestamo;
	private Cliente Dni;
	private LocalDate Fecha;
	private float ImporteConInteres;
	private float ImporteSolicitado;
	private int Plazo;
	private int Cuotas;
	private float MontoCuotas;
	private int Estado;
	private int CuotasPagadas;
	private Cuenta CuentaADepositar;
	
	public Prestamos(int prestamo, Cliente dni, LocalDate fecha, float importeConInteres, float importeSolicitado, int plazo,
			int cuotas, float montoCuotas, int estado, int cuota, Cuenta cueDepo) {
		
		Prestamo = prestamo;
		Dni = dni;
		Fecha = fecha;
		ImporteConInteres = importeConInteres;
		ImporteSolicitado = importeSolicitado;
		Plazo = plazo;
		Cuotas = cuotas;
		MontoCuotas = montoCuotas;
		Estado = estado;
		CuotasPagadas = cuota;
		CuentaADepositar=cueDepo;
	}
	
	public Prestamos() {}

	public int getPrestamo() {
		return Prestamo;
	}

	public void setPrestamo(int prestamo) {
		Prestamo = prestamo;
	}

	public Cliente getDni() {
		return Dni;
	}

	public void setDni(Cliente dni) {
		Dni = dni;
	}

	public LocalDate getFecha() {
		return Fecha;
	}

	public void setFecha(LocalDate fecha) {
		Fecha = fecha;
	}

	public float getImporteConInteres() {
		return ImporteConInteres;
	}

	public void setImporteConInteres(float importeConInteres) {
		ImporteConInteres = importeConInteres;
	}

	public float getImporteSolicitado() {
		return ImporteSolicitado;
	}

	public void setImporteSolicitado(float importeSolicitado) {
		ImporteSolicitado = importeSolicitado;
	}

	public int getPlazo() {
		return Plazo;
	}

	public void setPlazo(int plazo) {
		Plazo = plazo;
	}

	public int getCuotas() {
		return Cuotas;
	}

	public void setCuotas(int cuotas) {
		Cuotas = cuotas;
	}

	public float getMontoCuotas() {
		return MontoCuotas;
	}

	public void setMontoCuotas(float montoCuotas) {
		MontoCuotas = montoCuotas;
	}

	public int getEstado() {
		return Estado;
	}

	public void setEstado(int estado) {
		Estado = estado;
	}
	
	public int getCuotasPagadas() {
		return CuotasPagadas;
	}

	public void setCuotasPagadas(int cuotapagar) {
		CuotasPagadas = cuotapagar;
	}
	public Cuenta getCuentaADepositar() {
		return CuentaADepositar;
	}
	public void setCuentaADepositar(Cuenta cuDepo) {
		CuentaADepositar=cuDepo;
	}

	@Override
	public String toString() {
		return "Prestamo Prestamo: " + Prestamo + ", Dni: " + Dni + ", Fecha: " + Fecha + ", ImporteConInteres: "
				+ ImporteConInteres + ", ImporteSolicitado: " + ImporteSolicitado + ", Plazo: " + Plazo + ", Cuotas: "
				+ Cuotas + ", MontoCuotas: " + MontoCuotas + ", Estado: " + Estado + "" ;
	}
	
	
	public static boolean compararCuotas(Prestamos pre) throws CompararCuotasException {
		Boolean menor = false;
		
		if((pre.getCuotasPagadas()!=0) && (pre.getCuotasPagadas()>=pre.getCuotas())) {
			menor = true;
		}
		
		if(menor) {
			throw new CompararCuotasException();
		}
		return menor;
	}
	
}
