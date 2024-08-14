package Negocio;

import java.util.List;

import Dominio.Prestamos;

public interface INegocioPrestamos {
	public boolean insert(Prestamos pre);
	public boolean delete(Prestamos pre);
	public boolean update(Prestamos pre);
	public List<Prestamos> readall();
	public boolean cambiarEstado(Prestamos pre);
	public List<Prestamos> obtenerPorFecha(Prestamos preIn, Prestamos preFin);
	public Prestamos obtenerPorPrestamo(Prestamos pre);
}
