package NegocioImple;

import DaoImple.DaoTipoMovimiento;
import Dominio.TipoMovimiento;
import Negocio.INegocioTipoMovimiento;

public class NegocioTipoMovimiento implements INegocioTipoMovimiento{

	public TipoMovimiento obtenerPorMov(TipoMovimiento tpm) {
		DaoTipoMovimiento daotipoMovimiento=new DaoTipoMovimiento();
		tpm=daotipoMovimiento.obtenerPorMov(tpm);
		return tpm;
	}

}
