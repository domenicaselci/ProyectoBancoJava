package NegocioImple;

import java.util.ArrayList;
import java.util.List;

import DaoImple.DaoClienteImple;
import DaoImple.DaoCuentaImple;
import DaoImple.DaoUsuarioImple;
import Dominio.Cliente;
import Dominio.Usuarios;
import Negocio.INegocioUsuario;

public class NegocioUsuarioImple implements INegocioUsuario{

	@Override
	public boolean insert(Usuarios us) {
		Boolean Inserto = false;
		if(!existeUsuario(us)) {
			DaoUsuarioImple daoUs=new DaoUsuarioImple();
			Inserto=daoUs.insert(us);
		}
		return Inserto;
	}

	@Override
	public boolean modificar(Usuarios us) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bajalogica(Usuarios us) {
		DaoUsuarioImple daoUsu = new DaoUsuarioImple();
	    boolean estado=false;
	    estado=existeUsuario(us);
	    if(estado==true) {
	    	estado = daoUsu.updateEstado(us,false);
	    }
	    return estado;
	}
	public List<Usuarios> readAll() {
		DaoUsuarioImple daoUs =new DaoUsuarioImple();
		return daoUs.readall();
	}
	
	public boolean existeUsuario(Usuarios us) {
		DaoUsuarioImple daoUsuario = new DaoUsuarioImple();
		List<Usuarios> ListaUsu = new ArrayList<Usuarios>();
		ListaUsu = daoUsuario.readall();
		for(Usuarios u : ListaUsu) {
			if(u.getUsuario().equals(us.getUsuario())) {
				return true;
			};
		}
		return false;
	}
	
	public Usuarios obtenerporusu(Usuarios usu) {
		DaoUsuarioImple daou = new DaoUsuarioImple();
		Usuarios usuarios = new Usuarios();
		usuarios = (Usuarios) daou.buscarusu(usu);
		return usuarios;
	}
	
	public List<Usuarios> obtenerPorFecha(Usuarios usu, Usuarios usuFin){
		DaoUsuarioImple daou = new DaoUsuarioImple();
		return daou.buscarporFecha(usu, usuFin);
	}
}