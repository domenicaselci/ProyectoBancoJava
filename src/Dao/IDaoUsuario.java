package Dao;

import java.util.List;

import Dominio.Usuarios;

public interface IDaoUsuario {

	public boolean insert(Usuarios usu);
	public boolean delete(Usuarios usu);
	public List<Usuarios> readall();
	public boolean update(Usuarios usu);
	public Usuarios buscarusu(Usuarios usu);
	public List<Usuarios> buscarporFecha(Usuarios usu, Usuarios usuFin);
}
