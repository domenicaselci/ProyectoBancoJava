package Dao;

import java.util.List;

import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Provincia;

public interface IDaoMovimientos {
	
	public boolean insert(Movimiento mov);
	public List<Movimiento> ObtenerMovPorCuenta(Cuenta cue);
	public Movimiento ultimaTransferencia();
	public boolean transferencia(Movimiento mov);
}
