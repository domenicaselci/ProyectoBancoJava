package Dao;

import java.util.List;

import Dominio.Cliente;
import Dominio.Prestamos;

public interface IDaoPrestamos {
	public boolean insert(Prestamos pre);
	public boolean delete(Prestamos pre);
	public List<Prestamos> readall();
	public boolean update(Prestamos pre);
	public boolean updateEstado(Prestamos pre);
	public List<Prestamos> obtenerPrestamos(Prestamos preIn, Prestamos preFin);
	public Prestamos buscarporprestamo(Prestamos pre);
}
