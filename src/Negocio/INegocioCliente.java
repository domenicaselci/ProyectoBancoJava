package Negocio;

import java.util.List;


import Dominio.Cliente;
import Dominio.Usuarios;

public interface INegocioCliente {

	public boolean insert(Cliente cli);
	public boolean modificar(Cliente cli);
	public boolean bajalogica(Cliente cli);
	public Cliente obtenerpordni(Cliente cli);
	public List<Cliente> readAll();
	public Cliente obtenerPorusuario(Usuarios usu);
	List<Cliente> obtenerClienteLike(String Texto);

}
