package DaoImple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoCuenta;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Localidad;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.Provincia;
import Dominio.TipoDeCuenta;
import Dominio.Usuarios;

public class DaoCuentaImple implements IDaoCuenta {
	
	private static final String insert = "Insert into Cuenta (Cuenta, CBU, DNI, FechaDeCreacion, TipodeCuenta, Saldo, Estado) Values(?,?,?,?,?,?,?)";
	private static final String delete = "Delete from Cuenta Where Cuenta=?";
	private static final String readall = "Select * from Cuenta";
	private static final String update = "UPDATE Cuenta SET CBU=?, DNI=?, FechaDeCreacion=?, TipodeCuenta=?, Saldo=?, Estado=? WHERE Cuenta=?";
	private static final String updateEstado = "UPDATE Cuenta Set Estado=? where Cuenta=?";
	private static final String buscarporcuenta = "Select * from Cuenta where Cuenta=?";
	private static final String buscarpordni= "Select * from Cuenta where DNI=?";
	private static final String restarCuota = "UPDATE  Cuenta SET Saldo = Saldo-? WHERE Cuenta = ?";
	private static final String buscarporCBU="Select * from Cuenta where CBU=?";
	private static final String obtenerFiltroCuenta="SELECT * FROM cuenta WHERE Saldo BETWEEN ? AND ? ";
	private static final String obtenerFiltroFecha="SELECT * FROM cuenta WHERE FechadeCreacion BETWEEN ? AND ? ";
	
	
	
	
	@Override
	public boolean insert(Cuenta cu) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isInsertExitoso = false;
        try
        {
        	Date fechaCreacion = java.sql.Date.valueOf(cu.getFechadeCreacion());
        	
            statement = conexion.prepareStatement(insert);
            statement.setInt(1, cu.getCuenta());
            statement.setInt(2, cu.getCbu());
            statement.setInt(3, cu.getDni().getDni());
            statement.setDate(4, fechaCreacion);
            statement.setInt(5, cu.getTipoDeCuenta().getIdTipo());
            statement.setFloat(6, cu.getSaldo());
            statement.setBoolean(7, cu.isEstado());
       
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
	
	
	
	@Override
	public boolean delete(Cuenta cu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cuenta> readall() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Cuenta> Cuentas = new ArrayList<Cuenta>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				Cuentas.add(getCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return Cuentas;
	}


	@Override
	public boolean update(Cuenta cu) {
		PreparedStatement statement;
		Connection conexion=Conexion.getConexion().getSQLConexion();
		boolean ModificarExitoso=false;
		try {
			Date fechaCreacion = java.sql.Date.valueOf(cu.getFechadeCreacion());
			
				statement =conexion.prepareStatement(update);
	            statement.setInt(1, cu.getCbu());
	            statement.setInt(2, cu.getDni().getDni());
	            statement.setDate(3, fechaCreacion);
	            statement.setInt(4, cu.getTipoDeCuenta().getIdTipo());
	            statement.setFloat(5, cu.getSaldo());
	            statement.setBoolean(6, cu.isEstado());
	            statement.setInt(7, cu.getCuenta());
	             
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				ModificarExitoso = true;
			}
			
		}
		catch(SQLException e){
			
			e.printStackTrace();
			
		}
		
		return ModificarExitoso;
	}

	@Override
	public boolean updateEstado(Cuenta cu) {
		PreparedStatement statement;
		Connection conexion=Conexion.getConexion().getSQLConexion();
		boolean ModificarExitoso=false;
		try {
			
			statement =conexion.prepareStatement(updateEstado);
            statement.setInt(2, cu.getCuenta());
            statement.setBoolean(1, false);
            
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				ModificarExitoso = true;
			}
			
		}
		catch(SQLException e){
			
			e.printStackTrace();
			
		}
		return ModificarExitoso;
	}


	private Cuenta getCuenta(ResultSet resultSet) throws SQLException
	{
		Cliente cliente = new Cliente();
		TipoDeCuenta tipoC = new TipoDeCuenta();
		
		int cuenta = resultSet.getInt("Cuenta");
		int cbu = resultSet.getInt("CBU");
		cliente.setDni(resultSet.getInt("DNI"));
		LocalDate FechaCrea = (resultSet.getDate("FechaDeCreacion")).toLocalDate();
		tipoC.setIdTipo(resultSet.getInt("TipodeCuenta"));
		float Saldo = resultSet.getFloat("Saldo");
		Boolean Estado = resultSet.getBoolean("Estado");
		
		return new Cuenta(cuenta,cbu,cliente, FechaCrea,tipoC,Saldo,Estado);
	}
	
	public Cuenta buscarporcuenta(Cuenta cu) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Cuenta cuenta = new Cuenta();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(buscarporcuenta);
			statement.setInt(1, cu.getCuenta());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cuenta = getCuenta(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cuenta;
	}
	
	public List<Cuenta> buscarPorDni(Cuenta cu) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Cuenta> cuenta = new ArrayList<Cuenta>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(buscarpordni);
			statement.setInt(1, cu.getDni().getDni());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cuenta.add(getCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cuenta;
	}
	
	public boolean updateCuota(Cuenta cu) {
	    PreparedStatement statement;
	    Connection cn = Conexion.getConexion().getSQLConexion();
	    boolean cambioExitoso = false;
	    try {
	        statement = cn.prepareStatement(restarCuota);
	        statement.setFloat(1, cu.getSaldo());
	        statement.setInt(2, cu.getCuenta());
	        if (statement.executeUpdate() > 0) {
	            cn.commit();
	            cambioExitoso = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cambioExitoso;
	}
	
	public Cuenta buscarporCbu(Cuenta cu) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Cuenta cuenta = new Cuenta();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(buscarporCBU);
			statement.setInt(1, cu.getCbu());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cuenta = getCuenta(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cuenta;
	}
	
public List<Cuenta> ObtenerFiltroCuenta(float montini,float montofin) {
		
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Cuenta> listaCuentas=new ArrayList<Cuenta>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerFiltroCuenta);
			statement.setFloat(1,montini);
			statement.setFloat(2,montofin);
			
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				listaCuentas.add(getCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return listaCuentas;
	
}

public List<Cuenta> ObtenerFiltroFecha(LocalDate fechaIni,LocalDate fechaFin) {
	Date fechaInic = java.sql.Date.valueOf(fechaIni);
	Date fechaFina = java.sql.Date.valueOf(fechaFin);
	PreparedStatement statement;
	ResultSet resultSet;
	Conexion conexion = Conexion.getConexion();
	ArrayList<Cuenta> listaCuentas=new ArrayList<Cuenta>();
	try 
	{
		statement = conexion.getSQLConexion().prepareStatement(obtenerFiltroFecha);
		statement.setDate(1,fechaInic);
		statement.setDate(2,fechaFina);
		
		resultSet = statement.executeQuery();
		while(resultSet.next())
		{
			listaCuentas.add(getCuenta(resultSet));
		}
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	return listaCuentas;

}

	
	
}
