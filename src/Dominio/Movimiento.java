package Dominio;

import java.time.LocalDate;

public class Movimiento {

	private int Movimiento;
	private Cuenta Cuenta;
	private LocalDate Fecha;
	private String Detalle;
	private float Importe;
	private TipoMovimiento TipoMovimiento;
	private Cuenta CbuDestino;
	
	public Movimiento(int movimiento, Cuenta cuenta, LocalDate fecha, String detalle, float importe, Dominio.TipoMovimiento tipoMovimiento,
			Cuenta cbuDestino) {
	
		Movimiento = movimiento;
		Cuenta = cuenta;
		Fecha = fecha;
		Detalle = detalle;
		Importe = importe;
		TipoMovimiento = tipoMovimiento;
		CbuDestino = cbuDestino;
	}
	public Movimiento() {
		
	}
	
	public int getMovimiento() {
		return Movimiento;
	}

	public void setMovimiento(int movimiento) {
		Movimiento = movimiento;
	}

	public Cuenta getCuenta() {
		return Cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		Cuenta = cuenta;
	}

	public LocalDate getFecha() {
		return Fecha;
	}

	public void setFecha(LocalDate fecha) {
		Fecha = fecha;
	}

	public String getDetalle() {
		return Detalle;
	}

	public void setDetalle(String detalle) {
		Detalle = detalle;
	}

	public float getImporte() {
		return Importe;
	}

	public void setImporte(float importe) {
		Importe = importe;
	}

	public TipoMovimiento getTipoMovimiento() {
		return TipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		TipoMovimiento = tipoMovimiento;
	}

	public Cuenta getCbuDestino() {
		return CbuDestino;
	}

	public void setCbuDestino(Cuenta cbuDestino) {
		CbuDestino = cbuDestino;
	}
	@Override
	public String toString() {
		return "Movimiento Movimiento: " + Movimiento + ", Cuenta: " + Cuenta + ", Fecha: " + Fecha + ", Detalle: "
				+ Detalle + ", Importe: " + Importe + ", TipoMovimiento: " + TipoMovimiento + ", CbuDestino: " + CbuDestino
				+ "";
	}

}
