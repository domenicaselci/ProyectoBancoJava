package NegocioImple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DaoImple.DaoClienteImple;
import DaoImple.DaoCuentaImple;
import DaoImple.DaoMovimientosImple;
import DaoImple.DaoPrestamosImple;
import DaoImple.DaoProvinciaImple;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.Provincia;
import Negocio.INegocioCuenta;
import Negocio.INegocioMovimientos;

public class NegocioMovimientosImple implements INegocioMovimientos {


	@Override
	public boolean insert(Movimiento mov) {
		DaoMovimientosImple daoMov =new DaoMovimientosImple();
		Boolean inserto=false;
		inserto=daoMov.insert(mov);		
		return inserto;
	}

	public List<Movimiento> ObtenerMovimientosporCuenta(Cuenta cue) {
	DaoMovimientosImple daoMovimientosImple=new DaoMovimientosImple();
	ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
		lMovimientos=(ArrayList<Movimiento>)daoMovimientosImple.ObtenerMovPorCuenta(cue);
	
		return lMovimientos;
	}
	public boolean transferencia(Movimiento mov) {
		DaoMovimientosImple daoMov =new DaoMovimientosImple();
		Boolean transfirio=false;
		transfirio=daoMov.transferencia(mov);		
		return transfirio;
	}
	public Movimiento ultimatransfer() {
	    DaoMovimientosImple daoMov = new DaoMovimientosImple();
	    return daoMov.ultimaTransferencia();
	}
	public List<Movimiento> ObtenerMovimientosporFiltro(float montini, float montfin,Cuenta cuenta) {
		DaoMovimientosImple daoMovimientosImple=new DaoMovimientosImple();
		ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
		lMovimientos=(ArrayList<Movimiento>)daoMovimientosImple.ObtenerFiltro(montini, montfin, cuenta);
		
		NegocioTipoMovimiento negocioTipoMovimiento=new NegocioTipoMovimiento();
		
		for(Movimiento obj: lMovimientos ) {
			obj.setTipoMovimiento(negocioTipoMovimiento.obtenerPorMov(obj.getTipoMovimiento()));
	
		}
		return lMovimientos;
		
		
	}

	@Override
	public List<Movimiento> ObtenerFiltroFechaMovimiento(LocalDate fechaIni, LocalDate fechaFin, Cuenta cuenta) {
		DaoMovimientosImple daoMovimientosImple=new DaoMovimientosImple();
		ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
		lMovimientos=(ArrayList<Movimiento>)daoMovimientosImple.ObtenerFiltroFechaMovimiento(fechaIni, fechaFin, cuenta);
		NegocioTipoMovimiento negocioTipoMovimiento=new NegocioTipoMovimiento();
		
		for(Movimiento obj: lMovimientos ) {
			obj.setTipoMovimiento(negocioTipoMovimiento.obtenerPorMov(obj.getTipoMovimiento()));
			
			
		}
		return lMovimientos;
		
	}

	@Override
	public ArrayList<Movimiento> obtenerMovimientoLike(String Texto,int numeroCuenta) {
		DaoMovimientosImple daoMovimientosImple=new DaoMovimientosImple();
		ArrayList<Movimiento> lMovimiento=new ArrayList<Movimiento>();
		
		lMovimiento=daoMovimientosImple.obtenerMovimientoLike(Texto,numeroCuenta);
		NegocioTipoMovimiento negocioTipoMovimiento=new NegocioTipoMovimiento();

		for(Movimiento obj: lMovimiento ) {
			obj.setTipoMovimiento(negocioTipoMovimiento.obtenerPorMov(obj.getTipoMovimiento()));
			
			
		}
		
		return lMovimiento;
		
	}
}
