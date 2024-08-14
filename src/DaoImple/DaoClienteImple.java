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
import Dominio.Cliente;
import Dominio.Localidad;
import Dominio.Provincia;
import Dominio.Usuarios;

import DaoImple.Conexion;

public class DaoClienteImple implements IDaoCliente{

	private static final String insert = "Insert into Cliente (DNI, CUIL, Nombre, Apellido, Sexo, Nacionalidad, FechadeNacimiento, Direccion, IDProvincia, IDLocalidad, CorreoElectronico, Telefono, UsuarioCli, Estado) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String delete = "Delete from Cliente Where dni=?";
	private static final String readall = "Select * from Cliente";
	private static final String buscardni = "Select * from Cliente Where dni=?";
	private static final String update = "UPDATE Cliente SET CUIL=?,Nombre=?, Apellido=?, Sexo=?, Nacionalidad=?, FechadeNacimiento=?, Direccion=?, IDProvincia=?, IDLocalidad=?, CorreoElectronico=?, Telefono=?, Estado=? WHERE Dni = ?";
	private static final String updateEstado = "UPDATE Cliente Set Estado=? where dni=?";
	private static final String obtenerusuario = "Select * from Cliente where UsuarioCli=?";
	private static final String BuscarLike="Select * from Cliente where  Apellido like ? ";
	
	@Override
	public boolean insert(Cliente cli) {
		PreparedStatement statement;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        boolean isInsertExitoso = false;
        try
        {
        	Date fechaNacimientoSQL = java.sql.Date.valueOf(cli.getFechaDeNacimiento());
        	
            statement = conexion.prepareStatement(insert);
            statement.setInt(1, cli.getDni());
            statement.setString(2, cli.getCuil());
            statement.setString(3, cli.getNombre());
            statement.setString(4, cli.getApellido());
            statement.setString(5, cli.getSexo());
            statement.setString(6, cli.getNacionalidad());
            statement.setDate(7, fechaNacimientoSQL);
            statement.setString(8, cli.getDireccion());
            statement.setInt(9, cli.getProvincia().getIdProvincia());
            statement.setInt(10, cli.getLocalidad().getIdLocalidad());
            statement.setString(11, cli.getCorreoElectronico());
            statement.setInt(12, cli.getTelefono());
            statement.setString(13, cli.getUsuarioCli().getUsuario());
            statement.setBoolean(14, cli.isEstado());
       
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
	public boolean delete(Cliente cli) {
		PreparedStatement statement;
		Connection cn = Conexion.getConexion().getSQLConexion();
		boolean deleteExitoso = false;
		try 
		{
			statement = cn.prepareStatement(delete);
			statement.setInt(1,(cli.getDni()));
			if(statement.executeUpdate() > 0)
			{
				cn.commit();
				deleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return deleteExitoso;
	}

	@Override
	public List<Cliente> readall() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				clientes.add(getCliente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public boolean update(Cliente cli) {
		PreparedStatement statement;
		Connection conexion=Conexion.getConexion().getSQLConexion();
		boolean ModificarExitoso=false;
		try {
			Date fechaNacimientoSQL = java.sql.Date.valueOf(cli.getFechaDeNacimiento());
			
			statement =conexion.prepareStatement(update);
			statement.setString(1, cli.getCuil());
			statement.setString(2, cli.getNombre());
			statement.setString(3, cli.getApellido());
			statement.setString(4, cli.getSexo());
			statement.setString(5, cli.getNacionalidad());
			statement.setDate(6, fechaNacimientoSQL);
			statement.setString(7, cli.getDireccion());
			statement.setInt(8, cli.getProvincia().getIdProvincia());
			statement.setInt(9, cli.getLocalidad().getIdLocalidad());
			statement.setString(10, cli.getCorreoElectronico());
			statement.setInt(11, cli.getTelefono());
			statement.setBoolean(12, cli.isEstado());
			statement.setInt(13, cli.getDni());
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

	public boolean updateEstado(Cliente cli,Boolean estadoNuevo) {
		PreparedStatement statement;
		Connection conexion=Conexion.getConexion().getSQLConexion();
		boolean ModificarExitoso=false;
		try {
			
			statement =conexion.prepareStatement(updateEstado);
            statement.setInt(2, cli.getDni());
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

	
	private Cliente getCliente(ResultSet resultSet) throws SQLException
	{
		Localidad localidad = new Localidad();
		Provincia provincia = new Provincia();
		Usuarios usu = new Usuarios();
		
		int Dni = resultSet.getInt("DNI");
		String CUIL = resultSet.getString("CUIL");
		String Nombre = resultSet.getString("Nombre");
		String Apellido = resultSet.getString("Apellido");
		String Sexo = resultSet.getString("Sexo");
		String Nacionalidad =  resultSet.getString("Nacionalidad");
		LocalDate FechaNac = (resultSet.getDate("FechadeNacimiento")).toLocalDate();
		String Direccion = resultSet.getString("Direccion");
		provincia.setIdProvincia(resultSet.getInt("IDProvincia"));
		localidad.setIdLocalidad(resultSet.getInt("IDLocalidad"));
		String Correo =  resultSet.getString("CorreoElectronico");
		int Telefono = resultSet.getInt("Telefono");
		usu.setUsuario(resultSet.getString("UsuarioCli"));
		Boolean Estado = resultSet.getBoolean("Estado");
		
		return new Cliente(Dni,CUIL,Nombre, Apellido,Sexo,Nacionalidad,FechaNac,Direccion,provincia, localidad, Correo, Telefono,usu,Estado);
	}
	
	public Cliente buscardni(Cliente cli) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Cliente cliente = new Cliente();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(buscardni);
			statement.setInt(1, cli.getDni());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cliente = getCliente(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cliente;
	}
	
	public Cliente obtenerusuario(Usuarios usu) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Cliente cliente = new Cliente();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(obtenerusuario);
			statement.setString(1,usu.getUsuario());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cliente = getCliente(resultSet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cliente;
	}
	
	
	public ArrayList<Cliente> obtenerClienteLike(String Texto) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		Texto="%"+Texto+"%";
		ArrayList<Cliente> lClientes=new ArrayList<Cliente>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(BuscarLike);
			statement.setString(1,Texto);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				lClientes.add(getCliente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return lClientes;
	}
	
	
	
	
}
