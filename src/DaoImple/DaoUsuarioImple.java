package DaoImple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Dao.IDaoUsuario;
import Dominio.Cliente;
import Dominio.Usuarios;

public class DaoUsuarioImple implements IDaoUsuario{
	private static final String insert= "Insert into usuarios(Usuario,Contrasena,Estado,Fecha) values (?,?,?,?)";
	private static final String readall = "Select * from usuarios";
	private static final String obtenerporusuario = "Select * from usuarios where Usuario=?";
	private static final String obtenerfechaus ="Select * from usuarios where Fecha>=? and Fecha<=?";
	private static final String updateEstado = "UPDATE usuarios Set Estado=? where Usuario=?";
	
	@Override
	public boolean insert(Usuarios usu) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isInsertExitoso = false;
        try
        {       
        	Date fechaCreacion = java.sql.Date.valueOf(usu.getFechaCre());
        	
            statement = conexion.prepareStatement(insert);
            statement.setString(1, usu.getUsuario());
            statement.setString(2, usu.getContrasena());
            statement.setBoolean(3, true);    
            statement.setDate(4, fechaCreacion);
       
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
	public boolean delete(Usuarios usu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuarios> readall() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Usuarios> usuarios = new ArrayList<Usuarios>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				usuarios.add(getUsuario(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return usuarios;
	}

	@Override
	public boolean update(Usuarios usu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Usuarios getUsuario(ResultSet resultSet) throws SQLException
	{
		
		String Us = resultSet.getString("Usuario");
		String Contr = resultSet.getString("Contrasena");
		Boolean Estado = resultSet.getBoolean("Estado");
		LocalDate fecha = (resultSet.getDate("Fecha")).toLocalDate();
		
		Usuarios usu = new Usuarios(Us,Contr,Estado,fecha);
		
		return usu;
	}
	
	public Usuarios buscarusu(Usuarios usu) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Usuarios usuario = new Usuarios();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerporusuario);
			statement.setString(1, usu.getUsuario());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				usuario = getUsuario(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return usuario;
	}
	
	public List<Usuarios> buscarporFecha(Usuarios usu, Usuarios usuFin){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Usuarios> usus = new ArrayList<Usuarios>();
		try {
			Date fechaInicioSQL = java.sql.Date.valueOf(usu.getFechaCre());
			Date fechaFinSQL = java.sql.Date.valueOf(usuFin.getFechaCre());
			statement = conexion.getSQLConexion().prepareStatement(obtenerfechaus);
			statement.setDate(1, fechaInicioSQL);
			statement.setDate(2, fechaFinSQL);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				 usus.add(getUsuario(resultSet));
			}
		}catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return usus;
		
	}
	public boolean updateEstado(Usuarios us,Boolean estadoNuevo) {
		PreparedStatement statement;
		Connection conexion=Conexion.getConexion().getSQLConexion();
		boolean ModificarExitoso=false;
		try {
			
			statement =conexion.prepareStatement(updateEstado);
            statement.setString(2, us.getUsuario());
            statement.setBoolean(1, estadoNuevo);
            
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

}