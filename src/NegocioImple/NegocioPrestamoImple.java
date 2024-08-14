package NegocioImple;

import java.util.ArrayList;
import java.util.List;

import DaoImple.DaoClienteImple;
import DaoImple.DaoCuentaImple;
import DaoImple.DaoPrestamosImple;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Prestamos;
import Negocio.INegocioPrestamos;;

public class NegocioPrestamoImple implements INegocioPrestamos {
	
	@Override
	public List<Prestamos> readall() {
			DaoPrestamosImple daoPrestamos =new DaoPrestamosImple();
			return daoPrestamos.readall();
		}
	
	public boolean cambiarEstado(Prestamos pre) {
		DaoPrestamosImple daoprestamo = new DaoPrestamosImple();
	    boolean estado = false;
	    estado=existePrestamo(pre);
	    if(estado==true) {
	    	estado = daoprestamo.updateEstado(pre);
	    }
	    return estado;
	}
	
	public boolean existePrestamo(Prestamos pre) {
		int prestamo;
		prestamo = pre.getPrestamo();
		DaoPrestamosImple daoprestamo = new DaoPrestamosImple();
		List<Prestamos> ListaPrestamos = new ArrayList<Prestamos>();
		ListaPrestamos = daoprestamo.readall();
		for(Prestamos p : ListaPrestamos) {
			if(p.getPrestamo()==prestamo) {
				return true;
			};
		}
		return false;
		
	}

	@Override
	public boolean insert(Prestamos pre) {
		DaoPrestamosImple daoPrest= new DaoPrestamosImple();
		Boolean inserto=daoPrest.insert(pre);
		return inserto;
	}

	@Override
	public boolean delete(Prestamos pre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Prestamos pre) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Prestamos> obtenerPorFecha (Prestamos preIn, Prestamos preFin){
		DaoPrestamosImple daoPre = new DaoPrestamosImple();
		return daoPre.obtenerPrestamos(preIn,preFin);
	}
	public List<Prestamos> obtenerPorMonto (Prestamos mon, Prestamos monFin){
		DaoPrestamosImple daoPre = new DaoPrestamosImple();
		return daoPre.obtenerMonto(mon,monFin);
	}
	public boolean modificarCuota(Prestamos pre) {
		boolean modifico=false;
		DaoPrestamosImple daopres =new DaoPrestamosImple();
		modifico=daopres.updateCuota(pre);
		return modifico;
	}
	public List<Prestamos> obtenerPorDni (Prestamos pre){
		DaoPrestamosImple daoPre = new DaoPrestamosImple();
		return daoPre.obtenerPrestamos(pre);
	}
	
	public Prestamos obtenerPorPrestamo(Prestamos pre) {
		DaoPrestamosImple daopre = new DaoPrestamosImple();
		Prestamos pres = new Prestamos();
		pres = (Prestamos) daopre.buscarporprestamo(pre);
		return pres;
	}
	public Prestamos obtenerPorID(Prestamos pre) {
		Prestamos obtenido=new Prestamos();
		DaoPrestamosImple daoPrest=new DaoPrestamosImple();
		obtenido=daoPrest.obtenerPorID(pre);
		return obtenido;
	}
}

