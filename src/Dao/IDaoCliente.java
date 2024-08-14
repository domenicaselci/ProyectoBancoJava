package Dao;

import java.util.List;

import Dominio.Cliente;
import Dominio.Usuarios;

public interface IDaoCliente {
	public boolean insert(Cliente cli);
	public boolean delete(Cliente cli);
	public Cliente buscardni(Cliente cli);
	public List<Cliente> readall();
	public boolean update(Cliente cli);
	public Cliente obtenerusuario(Usuarios usu);
}
