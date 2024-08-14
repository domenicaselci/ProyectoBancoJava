package Dao;

import java.util.List;

import Dominio.Localidad;

public interface IDaoLocalidad {
	public boolean insert(Localidad loc);
	public boolean update(Localidad loc);
	public boolean delete(Localidad loc);
	public List<Localidad> readAll();
	public Localidad obtenerlocalidad(Localidad loc);
}
