package NegocioImple;

import java.util.List;

import DaoImple.DaoClienteImple;
import DaoImple.DaoProvinciaImple;
import Dominio.Cliente;
import Dominio.Provincia;
import Dominio.Usuarios;
import Negocio.INegocioProvincia;

public class NegocioProvinciaImple implements INegocioProvincia {

	@Override
	public boolean insert(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bajalogica(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Provincia> readAll() {
		DaoProvinciaImple daoProv=new DaoProvinciaImple();
		return daoProv.readAll();
	}
	
	public Provincia obtenerPorprov(Provincia pro) {
		DaoProvinciaImple daocli = new DaoProvinciaImple();
		Provincia prov = new Provincia();
		prov = (Provincia) daocli.obtenerprovincia(pro);
		return prov;
	}

}
