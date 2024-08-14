package NegocioImple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DaoImple.DaoClienteImple;
import DaoImple.DaoCuentaImple;
import DaoImple.DaoMovimientosImple;
import DaoImple.DaoPrestamosImple;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Negocio.INegocioCuenta;

public class NegocioCuentaImple implements INegocioCuenta {

	public boolean insert(Cuenta cu) {
		DaoCuentaImple daocuenta =new DaoCuentaImple();
		Boolean inserto=false;
		NegocioClienteImple negCli= new NegocioClienteImple();
		
		if(cu!=null) {
			if(negCli.existeCliente(cu.getDni())) {
				Cliente cli=negCli.obtenerpordni(cu.getDni());
				if(cli.isEstado()) {
					if(!existeCuenta(cu)) {
						inserto=daocuenta.insert(cu);
				}}}
		}
		return inserto;
	}
	
	public List<Cuenta> readAll() {
		DaoCuentaImple daocuenta =new DaoCuentaImple();
		return daocuenta.readall();
	}
	
	public boolean modificar(Cuenta cu) {
		boolean modifico=false;
		DaoCuentaImple daocuenta =new DaoCuentaImple();
		//if(VerificarCampos(cli)==true) {
			modifico=daocuenta.update(cu);
		//}
		return modifico;
	}
	
	public boolean existeCuenta(Cuenta cu) {
		int cuenta;
		cuenta = cu.getCuenta();
		DaoCuentaImple daocu =new DaoCuentaImple();
		List<Cuenta> ListaCuenta = new ArrayList<Cuenta>();
		ListaCuenta = daocu.readall();
		for(Cuenta c : ListaCuenta) {
			if(c.getCuenta()==cuenta) {
				return true;
			};
		}
		return false;
	}
	
	public boolean existeCBU(Cuenta cu) {
		int cbu;
		cbu = cu.getCbu();
		DaoCuentaImple daocu =new DaoCuentaImple();
		List<Cuenta> ListaCuenta = new ArrayList<Cuenta>();
		ListaCuenta = daocu.readall();
		for(Cuenta c : ListaCuenta) {
			if(c.getCbu()==cbu) {
				return true;
			};
		}
		return false;
	}
	
	public Cuenta obtenerporcuenta(Cuenta cu) {
		DaoCuentaImple daocu = new DaoCuentaImple();
		Cuenta cue = new Cuenta();
		cue = (Cuenta) daocu.buscarporcuenta(cu);
		return cue;
	}
	
	public List<Cuenta> obtenerPorDni(Cuenta cu) {
		DaoCuentaImple daocu = new DaoCuentaImple();
		return daocu.buscarPorDni(cu);
	}

	@Override
	public boolean BajaLogica(Cuenta cu) {
		DaoCuentaImple daoCu = new DaoCuentaImple();
	    boolean estado=false;
	    
	    estado=existeCuenta(cu);
	    if(estado==true) {
	    
	    	estado = daoCu.updateEstado(cu);
	    }
	    return estado;
	}
	
	public boolean contarCuentas(Cuenta cu) {
		DaoCuentaImple daoCu = new DaoCuentaImple();
		List<Cuenta> cuentas = daoCu.buscarPorDni(cu);
		if(cuentas.size()<3) {
			return true;
		}
		else return false;
	}
	
	public boolean modificarSaldo(Cuenta cu) {
		boolean modifico=false;
		DaoCuentaImple daoCu =new DaoCuentaImple();
		modifico=daoCu.updateCuota(cu);
		return modifico;
	}

	@Override
	public Cuenta buscarporCbu(Cuenta cu) {
		DaoCuentaImple daoCuentaImple=new DaoCuentaImple();
		Cuenta cuenta=new Cuenta();
		
		cuenta=daoCuentaImple.buscarporCbu(cu);
		return cuenta;
		
	}

	@Override
	public List<Cuenta> ObtenerFiltroCuenta(float montini, float montofin) {
		DaoCuentaImple daoCuentaImple=new DaoCuentaImple();
		ArrayList<Cuenta> lCuentas=new ArrayList<Cuenta>();
		lCuentas=(ArrayList<Cuenta>)daoCuentaImple.ObtenerFiltroCuenta(montini, montofin);
		return lCuentas;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cuenta> ObtenerFiltroFecha(LocalDate fechaIni, LocalDate fechaFin) {
		DaoCuentaImple daoCuentaImple=new DaoCuentaImple();
		ArrayList<Cuenta> lCuentas=new ArrayList<Cuenta>();
		lCuentas=(ArrayList<Cuenta>)daoCuentaImple.ObtenerFiltroFecha(fechaIni, fechaFin);
		return lCuentas;
		
	}
}
