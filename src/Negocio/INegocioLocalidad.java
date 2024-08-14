package Negocio;

import java.util.List;

import Dominio.Localidad;;

public interface INegocioLocalidad {
	public boolean insert(Localidad loc);
	public boolean modificar(Localidad loc);
	public boolean bajalogica(Localidad loc);
	public List<Localidad> readAll();
	public Localidad obtenerPorLoc(Localidad loc);
	
}
