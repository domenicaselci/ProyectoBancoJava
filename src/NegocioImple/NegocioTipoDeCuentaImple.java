package NegocioImple;

import java.util.ArrayList;
import java.util.List;

import Dao.IDaoTipoDeCuenta;
import DaoImple.DaoLocalidadImple;
import DaoImple.DaoTipoDeCuentaImple;
import Dominio.Localidad;
import Dominio.TipoDeCuenta;
import Negocio.INegocioTipoCuenta;

public class NegocioTipoDeCuentaImple implements INegocioTipoCuenta {

	

	public List<TipoDeCuenta> readAll() {
		DaoTipoDeCuentaImple daoTdC=new DaoTipoDeCuentaImple();
		return daoTdC.readAll();
	}
	
	


	@Override
	public TipoDeCuenta obtenerTipoPorID(TipoDeCuenta tdCuenta) {
		DaoTipoDeCuentaImple dtipod = new DaoTipoDeCuentaImple();
		TipoDeCuenta tp=new TipoDeCuenta();
		tp=dtipod.obtenerTipoDecuenta(tdCuenta);
		return tp;
	}


	


	@Override
	public boolean modificar(TipoDeCuenta tdC) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean bajalogica(TipoDeCuenta tdC) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean insert(TipoDeCuenta tdC) {
		// TODO Auto-generated method stub
		return false;
	}

}
