package Dao;

import java.util.List;

import Dominio.Provincia;

public interface IDaoProvincia {
	public boolean insert(Provincia prov);
	public boolean update(Provincia prov);
	public boolean delete(Provincia prov);
	public List<Provincia> readAll();
	public Provincia obtenerprovincia(Provincia prov);
}
