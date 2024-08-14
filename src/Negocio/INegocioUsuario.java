package Negocio;

import java.util.List;

import Dominio.Usuarios;

public interface INegocioUsuario {
	public boolean insert(Usuarios us);
	public boolean modificar(Usuarios us);
	public boolean bajalogica(Usuarios us);
	public List<Usuarios> readAll();
	public boolean existeUsuario(Usuarios us);
	public Usuarios obtenerporusu(Usuarios us);
	public List<Usuarios> obtenerPorFecha(Usuarios us, Usuarios usFin);

}