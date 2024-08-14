package DaoImple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoLocalidad;
import Dominio.Localidad;
import Dominio.Provincia;


public class DaoLocalidadImple implements IDaoLocalidad {

	private static final String readall = "Select IDLocalidad AS ID, IDProvincia AS Provincia, Nombre AS Localidad from Localidad";
	private static final String obtenerLocalidad = "Select IDLocalidad AS ID, IDProvincia AS Provincia, Nombre AS Localidad from Localidad where IDLocalidad=?";
	
	@Override
	public boolean insert(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Localidad loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Localidad> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Localidad> localidades = new ArrayList<Localidad>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				localidades.add(getLocalidad(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}
	
	private Localidad getLocalidad(ResultSet resultSet) throws SQLException
	{
		
		int IdLoc = resultSet.getInt("ID");
		int IdProv = resultSet.getInt("Provincia");
		String Nombre = resultSet.getString("Localidad");
		
		Provincia prov=new Provincia();
		prov.setIdProvincia(IdProv);
		
		Localidad objLocalidad = new Localidad(IdLoc,prov,Nombre);
		
		return objLocalidad;
	}

	public Localidad obtenerlocalidad(Localidad loc) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Localidad loca = new Localidad();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerLocalidad);
			statement.setInt(1, loc.getIdLocalidad());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				loca = getLocalidad(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return loca;
	}
}
