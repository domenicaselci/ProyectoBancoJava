package DaoImple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoCliente;
import Dao.IDaoMovimientos;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Localidad;
import Dominio.Movimiento;
import Dominio.Provincia;
import Dominio.TipoDeCuenta;
import Dominio.TipoMovimiento;
import Dominio.Usuarios;

import DaoImple.Conexion;

public class DaoMovimientosImple implements IDaoMovimientos{
	private static final String insert = "Insert into movimiento (Cuenta,Fecha,Detalle,Importe,TipoDeMovimiento,CBUDestino) values (?,?,?,?,?,?)";
	private static final String readall = "Select * from Movimiento";
	private static final String obtenerMov="select * from Movimiento where Cuenta=?";
	private static final String ultimatransfer = "Select MAX(Movimiento) from Movimiento where TipoDeMovimiento = 4";
	private static final String transferencia = "{call Transferencia(?,?,?,?,?)}";
	private static final String obtenerFiltro="SELECT * FROM movimiento WHERE Importe BETWEEN ? AND ? AND Cuenta = ?";
	private static final String obtenerFiltroFechaMov="SELECT * FROM Movimiento  WHERE Fecha BETWEEN ? AND ?  AND Cuenta=?";
	private static final String BuscarLikeDetalle="Select * from Movimiento where  Detalle like ? and Cuenta=?";
	
	@Override
	public boolean insert(Movimiento mov) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isInsertExitoso = false;
        try
        {
        	Date fecha = java.sql.Date.valueOf(mov.getFecha());
        	
            statement = conexion.prepareStatement(insert);
            statement.setInt(1, mov.getCuenta().getCuenta());
            statement.setDate(2, fecha);
            statement.setString(3,mov.getDetalle());
            statement.setFloat(4, mov.getImporte());
            statement.setInt(5, mov.getTipoMovimiento().getIdTipo());
            statement.setInt(6, mov.getCbuDestino().getCbu());
       
            if(statement.executeUpdate() > 0)
            {
                conexion.commit();
                isInsertExitoso = true;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return isInsertExitoso;
	}

	private Movimiento getMovimientos(ResultSet resultSet) throws SQLException
	{
		
		Cuenta cuenta=new Cuenta();
		TipoMovimiento TipoMovimiento=new TipoMovimiento();
		int mov= resultSet.getInt("Movimiento");
		cuenta.setCuenta(resultSet.getInt("Cuenta"));
		
		LocalDate Fecha = (resultSet.getDate("Fecha")).toLocalDate();
		
		String Detalle=resultSet.getString("Detalle");
		float importe=resultSet.getFloat("Importe");
		TipoMovimiento.setIdTipo(resultSet.getInt("TipoDeMovimiento"));
		cuenta.setCbu(resultSet.getInt("CBUDestino"));
		
		
		
		
		
		return new  Movimiento(mov,cuenta,Fecha,Detalle,importe,TipoMovimiento,cuenta) ;
	}
	
	public List<Movimiento> ObtenerMovPorCuenta(Cuenta cue) {
	
			PreparedStatement statement;
			ResultSet resultSet;
			Conexion conexion = Conexion.getConexion();
			ArrayList<Movimiento> listaMovimientos=new ArrayList<Movimiento>();
			try 
			{
				statement = conexion.getSQLConexion().prepareStatement(obtenerMov);
				statement.setInt(1,cue.getCuenta());
				resultSet = statement.executeQuery();
				while(resultSet.next())
				{
					listaMovimientos.add(getMovimientos(resultSet));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return listaMovimientos;
		
	}

	@Override
	public boolean transferencia(Movimiento mov) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isTransExitosa = false;
        try
        {
        	Date fecha = java.sql.Date.valueOf(mov.getFecha());
        	
            statement = conexion.prepareStatement(transferencia);
            statement.setInt(1, mov.getCuenta().getCuenta());
            statement.setInt(2, mov.getCbuDestino().getCbu());
            statement.setString(3,mov.getDetalle());
            statement.setDate(4, fecha);
            statement.setFloat(5, mov.getImporte());
       
            if(statement.executeUpdate() > 0)
            {
                conexion.commit();
                isTransExitosa = true;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return isTransExitosa;
	}
	
	@Override
	public Movimiento ultimaTransferencia() {
	    PreparedStatement statement;
	    ResultSet resultSet;
	    Conexion conexion = Conexion.getConexion();
	    int numeroMovimiento = 0;  
	    Movimiento mo = new Movimiento();
	    try {
	        statement = conexion.getSQLConexion().prepareStatement(ultimatransfer);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            numeroMovimiento = resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        conexion.cerrarConexion();
	    }
	    mo.setMovimiento(numeroMovimiento);
	    return mo;
	    
	}
public List<Movimiento> ObtenerFiltro(float montini,float montofin,Cuenta cuenta) {
		
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Movimiento> listaMovimientos=new ArrayList<Movimiento>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerFiltro);
			statement.setFloat(1,montini);
			statement.setFloat(2,montofin);
			statement.setInt(3,cuenta.getCuenta());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				listaMovimientos.add(getMovimientos(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return listaMovimientos;
	
}

public List<Movimiento> ObtenerFiltroFechaMovimiento(LocalDate fechaIni,LocalDate fechaFin,Cuenta cuenta) {
	Date fechaInic = java.sql.Date.valueOf(fechaIni);
	Date fechaFina = java.sql.Date.valueOf(fechaFin);
	PreparedStatement statement;
	ResultSet resultSet;
	Conexion conexion = Conexion.getConexion();
	ArrayList<Movimiento> listaMovimientos=new ArrayList<Movimiento>();
	try 
	{
		statement = conexion.getSQLConexion().prepareStatement(obtenerFiltroFechaMov);
		statement.setDate(1,fechaInic);
		statement.setDate(2,fechaFina);
		statement.setInt(3,cuenta.getCuenta());
		resultSet = statement.executeQuery();
		while(resultSet.next())
		{
			listaMovimientos.add(getMovimientos(resultSet));
		}
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	return listaMovimientos;

}

public ArrayList<Movimiento> obtenerMovimientoLike(String Texto,int numeroCuenta) {
	PreparedStatement statement;
	ResultSet resultSet;
	Conexion conexion = Conexion.getConexion();
	Texto="%"+Texto+"%";
	ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
	try 
	{
		statement = conexion.getSQLConexion().prepareStatement(BuscarLikeDetalle);
		statement.setString(1,Texto);
		statement.setInt(2,numeroCuenta);
		resultSet = statement.executeQuery();
		while(resultSet.next())
		{
			lMovimientos.add(getMovimientos(resultSet));
		}
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	return lMovimientos;
}




	

}

