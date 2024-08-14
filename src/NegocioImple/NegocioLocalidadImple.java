package NegocioImple;

import java.util.List;

import DaoImple.DaoLocalidadImple;
import DaoImple.DaoProvinciaImple;
import Dominio.Localidad;
import Dominio.Provincia;
import Negocio.INegocioLocalidad;

public class NegocioLocalidadImple implements INegocioLocalidad {

	@Override
	public boolean insert(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bajalogica(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Localidad> readAll() {
		DaoLocalidadImple daoLoc=new DaoLocalidadImple();		
		return daoLoc.readAll();
	}

	public Localidad obtenerPorLoc(Localidad loc) {
		DaoLocalidadImple daoLoc = new DaoLocalidadImple();
		Localidad loca = new Localidad();
		loca = (Localidad) daoLoc.obtenerlocalidad(loc);
		return loca;
	}
}
