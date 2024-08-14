package Negocio;

import java.util.List;

import Dominio.Provincia;

public interface INegocioProvincia {
	public boolean insert(Provincia prov);
	public boolean modificar(Provincia prov);
	public boolean bajalogica(Provincia prov);
	public List<Provincia> readAll();
	public Provincia obtenerPorprov (Provincia prov);
}
