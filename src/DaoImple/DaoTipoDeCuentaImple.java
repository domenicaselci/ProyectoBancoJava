package DaoImple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoTipoDeCuenta;
import Dominio.Localidad;
import Dominio.TipoDeCuenta;

public class DaoTipoDeCuentaImple implements IDaoTipoDeCuenta {
	private static final String readall = "Select * from tipodecuenta";
	private static final String obtenerTipoDecuenta= "Select * from TipoDeCuenta where IDTipo=?";

	public boolean insert(TipoDeCuenta tdCuenta) {

		return false;
	}

	
	public boolean update(TipoDeCuenta tdCuenta) {
		
		return false;
	}


	public boolean delete(TipoDeCuenta tdCuenta) {
		
		return false;
	}

	
	public List<TipoDeCuenta> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<TipoDeCuenta> tiposDeCuentas = new ArrayList<TipoDeCuenta>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				tiposDeCuentas.add(getTipoDeCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tiposDeCuentas;
	}
	
	private TipoDeCuenta getTipoDeCuenta(ResultSet resultSet) throws SQLException
	{	
		int IdTipo = resultSet.getInt("IDTipo");
		String Descripcion = resultSet.getString("Descripcion");
		
		TipoDeCuenta tdc=new TipoDeCuenta(IdTipo,Descripcion);
		
		return tdc;
	}
	
	
	


	@Override
	public TipoDeCuenta obtenerTipoDecuenta(TipoDeCuenta tdCuenta) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		TipoDeCuenta tipoDeCuenta = new TipoDeCuenta();
	
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerTipoDecuenta);
			statement.setInt(1,tdCuenta.getIdTipo());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				tipoDeCuenta=getTipoDeCuenta(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tipoDeCuenta;
	}

}
