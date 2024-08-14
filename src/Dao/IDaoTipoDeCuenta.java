package Dao;

import java.util.List;

import Dominio.TipoDeCuenta;


public interface IDaoTipoDeCuenta {
	public boolean insert(TipoDeCuenta tdCuenta);
	public boolean update(TipoDeCuenta tdCuenta);
	public boolean delete(TipoDeCuenta tdCuenta);
	public List<TipoDeCuenta> readAll();
	public TipoDeCuenta obtenerTipoDecuenta(TipoDeCuenta tdCuenta);
}
