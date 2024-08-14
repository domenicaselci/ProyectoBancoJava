package Dao;

import java.util.List;

import Dominio.Cuenta;

public interface IDaoCuenta {
	
	public boolean insert(Cuenta cu);
	public boolean delete(Cuenta cu);
	public List<Cuenta> readall();
	public boolean update(Cuenta cu);
	public boolean updateEstado(Cuenta cu);
	public boolean updateCuota(Cuenta cu);
	
}
