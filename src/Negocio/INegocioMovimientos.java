package Negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.Provincia;

public interface INegocioMovimientos {
	public boolean insert(Movimiento mov);
	public List<Movimiento> ObtenerMovimientosporCuenta(Cuenta cue);
	public boolean transferencia(Movimiento mov);
	List<Movimiento> ObtenerFiltroFechaMovimiento(LocalDate fechaIni, LocalDate fechaFin, Cuenta cuenta);
	ArrayList<Movimiento> obtenerMovimientoLike(String Texto, int numeroCuenta);
}
