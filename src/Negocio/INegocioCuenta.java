package Negocio;

import java.time.LocalDate;
import java.util.List;

import Dominio.Cliente;
import Dominio.Cuenta;

public interface INegocioCuenta {
	public boolean insert(Cuenta cu);
	public List<Cuenta> readAll();
	public boolean BajaLogica(Cuenta cu);
	public boolean modificarSaldo(Cuenta cu);
	public Cuenta buscarporCbu(Cuenta cu);
	List<Cuenta> ObtenerFiltroFecha(LocalDate fechaIni, LocalDate fechaFin);
	List<Cuenta> ObtenerFiltroCuenta(float montini, float montofin);
}
