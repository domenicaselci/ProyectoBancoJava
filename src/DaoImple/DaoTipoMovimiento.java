package DaoImple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.IDaoTipoMovimiento;

import Dominio.TipoMovimiento;

public class DaoTipoMovimiento implements IDaoTipoMovimiento{

	private static final String obtenerTipoMovimiento= "Select * from tipodemovimiento where IDTipo=?";
	
	
	private TipoMovimiento getTipoMovimiento(ResultSet resultSet) throws SQLException
	{	
		int IdTipo = resultSet.getInt("IDTipo");
		String Descripcion = resultSet.getString("Descripcion");
		
		TipoMovimiento tdm=new TipoMovimiento(IdTipo,Descripcion);
		
		return tdm;
	}
	



	public TipoMovimiento obtenerPorMov(TipoMovimiento tpm) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		TipoMovimiento tipoDeMovimiento = new TipoMovimiento();
	
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerTipoMovimiento);
			statement.setInt(1,tpm.getIdTipo());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				tipoDeMovimiento=getTipoMovimiento(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tipoDeMovimiento;
	}
}
