package DaoImple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoProvincia;
import Dominio.Cuenta;
import Dominio.Provincia;

public class DaoProvinciaImple implements IDaoProvincia{
	
	private static final String readall = "Select IDProvincia AS ID, Nombre AS Provincia from provincia";
	private static final String obtenerprovincia = "Select IDProvincia AS ID, Nombre AS Provincia from provincia where IDProvincia=?";
	
	@Override
	public boolean insert(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Provincia prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Provincia> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Provincia> localidades = new ArrayList<Provincia>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				localidades.add(getProvincia(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}
	
	public Provincia obtenerprovincia(Provincia pro) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Provincia prov = new Provincia();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerprovincia);
			statement.setInt(1, pro.getIdProvincia());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prov = getProvincia(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prov;
	}
	
	private Provincia getProvincia(ResultSet resultSet) throws SQLException
	{
		
		int IdProv = resultSet.getInt("ID");
		String Nombre = resultSet.getString("Provincia");
		
		Provincia prov=new Provincia(IdProv,Nombre);
		
		return prov;
	}

}
