package DaoImple;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoPrestamos;
import Dominio.Cliente;
import Dominio.Cuenta;

import Dominio.Prestamos;

public class DaoPrestamosImple implements IDaoPrestamos{
	private static final String insert="INSERT INTO prestamo(DNI,Fecha,ImporteconInteres,ImporteSolicitado,Plazo,Cuotas,MontoCuota,CuentaADepositar,Estado) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String readall = "Select * from prestamo";
	private static final String buscarprestamo = "Select * from prestamo where Prestamo=?";
	private static final String obtenerPrestamosCliente = "Select * from prestamo where DNI=?";
	private static final String updateEstado = "UPDATE  prestamo SET Estado= ? WHERE Prestamo = ?";
	private static final String obtenerfechapres ="Select * from prestamo where Fecha>=? and Fecha<=?";
	private static final String obtenerDinero = "Select * from prestamo where ImporteconInteres>=? and ImporteconInteres<=?";
	private static final String pagarCuota = "UPDATE  prestamo SET CuotasPagadas = CuotasPagadas+1 WHERE Prestamo = ?";
	private static final String obtenerPorID="Select * from prestamo where Prestamo=?";
	
	@Override
	public boolean insert(Prestamos pre) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isInsertExitoso = false;
        try
        {
        	Date fechaSolicitado = java.sql.Date.valueOf(pre.getFecha());
        	
            statement = conexion.prepareStatement(insert);
            statement.setInt(1, pre.getDni().getDni());
            statement.setDate(2, fechaSolicitado);
            statement.setFloat(3,pre.getImporteConInteres());
            statement.setFloat(4, pre.getImporteSolicitado());
            statement.setInt(5, pre.getPlazo());
            statement.setInt(6, pre.getCuotas());
            statement.setFloat(7, pre.getMontoCuotas());
            statement.setInt(8, pre.getCuentaADepositar().getCuenta());
            statement.setInt(9, pre.getEstado());
                   
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
	public boolean delete(Prestamos pre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Prestamos> readall() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Prestamos> prestamos = new ArrayList<Prestamos>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prestamos.add(getPrestamos(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prestamos;
	}

	
	@Override
	public boolean updateEstado(Prestamos pre) {
	    PreparedStatement statement;
	    Connection cn = Conexion.getConexion().getSQLConexion();
	    boolean cambioExitoso = false;
	    try {
	        statement = cn.prepareStatement(updateEstado);
	        statement.setInt(1, pre.getEstado());
	        statement.setInt(2, pre.getPrestamo());
	        if (statement.executeUpdate() > 0) {
	            cn.commit();
	            cambioExitoso = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cambioExitoso;
	}
	
	
	public boolean updateCuota(Prestamos pre) {
	    PreparedStatement statement;
	    Connection cn = Conexion.getConexion().getSQLConexion();
	    boolean cambioExitoso = false;
	    try {
	        statement = cn.prepareStatement(pagarCuota);
	        statement.setInt(1, pre.getPrestamo());
	        if (statement.executeUpdate() > 0) {
	            cn.commit();
	            cambioExitoso = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cambioExitoso;
	}
	
	
	
	private Prestamos getPrestamos(ResultSet resultSet) throws SQLException
    {
         int prestamoId = resultSet.getInt("Prestamo");
            int dni = resultSet.getInt("DNI");
            Cliente cli = new Cliente();
            cli.setDni(dni);
            Cuenta cu=new Cuenta();
            cu.setCuenta(resultSet.getInt("CuentaADepositar"));
            LocalDate fecha = resultSet.getDate("Fecha").toLocalDate();

            float importeConInteres = resultSet.getFloat("ImporteconInteres");
            float importeSolicitado = resultSet.getFloat("ImporteSolicitado");
            int plazo = resultSet.getInt("Plazo");
            int cuotas = resultSet.getInt("Cuotas");
            float montoCuota = resultSet.getFloat("MontoCuota");
            int estado = resultSet.getInt("Estado");
            int cuotaPagar = resultSet.getInt("CuotasPagadas");


            Prestamos prestamo=new Prestamos(prestamoId, cli, fecha, importeConInteres, importeSolicitado, plazo, cuotas, montoCuota, estado,cuotaPagar,cu);

            return prestamo;
    }

	@Override
	public boolean update(Prestamos pre) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Prestamos> obtenerPrestamos(Prestamos preIn, Prestamos preFin){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Prestamos> prestamo = new ArrayList<Prestamos>();
		try 
		{
			Date fechaInicioSQL = java.sql.Date.valueOf(preIn.getFecha());
			Date fechaFinSQL = java.sql.Date.valueOf(preFin.getFecha());
			statement = conexion.getSQLConexion().prepareStatement(obtenerfechapres);
			statement.setDate(1, fechaInicioSQL);
			statement.setDate(2, fechaFinSQL);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prestamo.add(getPrestamos(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prestamo;
	}
	
	public List<Prestamos> obtenerMonto(Prestamos mon, Prestamos monFin){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Prestamos> prestamo = new ArrayList<Prestamos>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerDinero);
			statement.setFloat(1, mon.getImporteConInteres());
			statement.setFloat(2, monFin.getImporteConInteres());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prestamo.add(getPrestamos(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prestamo;
	}
	
	public List<Prestamos> obtenerPrestamos(Prestamos pres){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Prestamos> prestamo = new ArrayList<Prestamos>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerPrestamosCliente);
			statement.setInt(1, pres.getDni().getDni());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prestamo.add(getPrestamos(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prestamo;
	}
	
	public Prestamos buscarporprestamo(Prestamos pre) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Prestamos prest = new Prestamos();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(buscarprestamo);
			statement.setInt(1, pre.getPrestamo());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				prest = getPrestamos(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return prest;
	}
	public Prestamos obtenerPorID(Prestamos pres){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Prestamos p=new Prestamos();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerPorID);
			statement.setInt(1, pres.getPrestamo());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				p=getPrestamos(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return p;
	}
}
