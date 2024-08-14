package NegocioImple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;

import DaoImple.DaoClienteImple;
import Negocio.INegocioCliente;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Usuarios;

public class NegocioClienteImple implements INegocioCliente{
	
	public boolean insert(Cliente cli) {
		DaoClienteImple daocliente =new DaoClienteImple();
		Boolean inserto=false;
		if(!existeCliente(cli)) {
			inserto=daocliente.insert(cli);
		}	
		return inserto;
	}
	
	
	
	public boolean bajalogica(Cliente cli) {		
		DaoClienteImple daoCliente = new DaoClienteImple();
		NegocioUsuarioImple negUsu=new NegocioUsuarioImple();
		NegocioCuentaImple negCue=new NegocioCuentaImple();
	    boolean estado=false;
	    estado=existeCliente(cli);
	    
	    Usuarios usuario=cli.getUsuarioCli();
	    if(estado==true) {	
	    	
	    	estado=negUsu.bajalogica(usuario);
	    	if(estado) {
	    		Cuenta cu=new Cuenta();
	    		cu.setDni(cli);
	    		ArrayList<Cuenta> cuentasCli=(ArrayList<Cuenta>) negCue.obtenerPorDni(cu);
	    		for(Cuenta c:cuentasCli) {
	    			estado=negCue.BajaLogica(c);
	    		}
	    		if(estado) {
	    		estado = daoCliente.updateEstado(cli,false);}
	    	}
	    }
	    return estado;
	}
	
	public boolean modificar(Cliente cli) {
		boolean modifico=false;
		DaoClienteImple daocliente =new DaoClienteImple();
		//if(VerificarCampos(cli)==true) {
			modifico=daocliente.update(cli);
		//}
		return modifico;
	}
	
	@Override
	public List<Cliente> readAll() {
		DaoClienteImple daocliente =new DaoClienteImple();
		NegocioLocalidadImple negLocalidad=new NegocioLocalidadImple();
		NegocioProvinciaImple negProvincia=new NegocioProvinciaImple();
		List<Cliente> listClientes=daocliente.readall();
		
		
		for(Cliente c:listClientes) {
			c.setLocalidad(negLocalidad.obtenerPorLoc(c.getLocalidad()));			
			c.setIdProvincia(negProvincia.obtenerPorprov(c.getProvincia()));
		}
		
		return listClientes;
	}
	
	public boolean existeCliente(Cliente cli) {
		int dni;
		dni = cli.getDni();
		DaoClienteImple daocliente =new DaoClienteImple();
		List<Cliente> ListaCliente = new ArrayList<Cliente>();
		ListaCliente = daocliente.readall();
		for(Cliente c : ListaCliente) {
			if(c.getDni()==dni) {
				return true;
			};
		}
		return false;
	}
	
	public Cliente obtenerpordni(Cliente cli) {
		
		DaoClienteImple daocli = new DaoClienteImple();
		Cliente cliente = new Cliente();
		cliente = (Cliente) daocli.buscardni(cli);
		NegocioLocalidadImple negLocalidad=new NegocioLocalidadImple();
		NegocioProvinciaImple negProvincia=new NegocioProvinciaImple();
		cliente.setLocalidad(negLocalidad.obtenerPorLoc(cliente.getLocalidad()));
		cliente.setIdProvincia(negProvincia.obtenerPorprov(cliente.getProvincia()));
		return cliente;
	}
	
	public Cliente obtenerPorusuario(Usuarios usu) {
		DaoClienteImple daocli = new DaoClienteImple();
		Cliente cliente = new Cliente();
		NegocioProvinciaImple negProv = new NegocioProvinciaImple();
		NegocioLocalidadImple negLoc = new NegocioLocalidadImple();
		cliente = (Cliente) daocli.obtenerusuario(usu);
		cliente.setIdProvincia(negProv.obtenerPorprov(cliente.getProvincia()));
		cliente.setLocalidad(negLoc.obtenerPorLoc(cliente.getLocalidad()));
		return cliente;
	}

	@Override
	public List<Cliente> obtenerClienteLike(String Texto) {
		DaoClienteImple daoClienteImple=new DaoClienteImple();
		ArrayList<Cliente> lClientes=new ArrayList<Cliente>();
		
		lClientes=daoClienteImple.obtenerClienteLike(Texto);
		NegocioProvinciaImple negProv = new NegocioProvinciaImple();
		NegocioLocalidadImple negLoc = new NegocioLocalidadImple();
		for(Cliente obj:lClientes) {
			obj.setIdProvincia(negProv.obtenerPorprov(obj.getProvincia()));
			obj.setLocalidad(negLoc.obtenerPorLoc(obj.getLocalidad()));
			
		}
		
		
		
		return lClientes;
		
		
	}
	
	
}

