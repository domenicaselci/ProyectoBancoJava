package Dominio;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Excepciones.CompararCuilException;
import Excepciones.compararDniException;


public class Cliente {
	private int Dni;
	private String Cuil;
	private String Nombre;
	private String Apellido;
	private String Sexo;
	private String Nacionalidad;
	private LocalDate FechaDeNacimiento;
	private String Direccion;
	private Provincia Provincia;
	private Localidad Localidad;
	private String CorreoElectronico;
	private int Telefono;
	private Usuarios UsuarioCli;
	private boolean Estado;
	
	public Cliente() {		
	}
	
	public Cliente(int dni, String cuil, String nombre, String apellido, String sexo, String nacionalidad,
			LocalDate fechaDeNacimiento, String direccion, Provincia idProvincia, Localidad idLocalidad, String correoElectronico,
			int telefono, Usuarios usuarioCli, boolean estado) {
		
		Dni = dni;
		Cuil = cuil;
		Nombre = nombre;
		Apellido = apellido;
		Sexo = sexo;
		Nacionalidad = nacionalidad;
		FechaDeNacimiento = fechaDeNacimiento;
		Direccion = direccion;
		Provincia = idProvincia;
		Localidad = idLocalidad;
		CorreoElectronico = correoElectronico;
		Telefono = telefono;
		UsuarioCli = usuarioCli;
		Estado = estado;
	}

	
public int getDni() {
	return Dni;
}
public void setDni(int dni) {
	Dni = dni;
}
public String getCuil() {
	return Cuil;
}
public void setCuil(String cuil) {
	Cuil = cuil;
}
public String getNombre() {
	return Nombre;
}
public void setNombre(String nombre) {
	Nombre = nombre;
}
public String getApellido() {
	return Apellido;
}
public void setApellido(String apellido) {
	Apellido = apellido;
}
public String getSexo() {
	return Sexo;
}
public void setSexo(String sexo) {
	Sexo = sexo;
}
public String getNacionalidad() {
	return Nacionalidad;
}
public void setNacionalidad(String nacionalidad) {
	Nacionalidad = nacionalidad;
}
public LocalDate getFechaDeNacimiento() {
	return FechaDeNacimiento;
}
public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
	FechaDeNacimiento = fechaDeNacimiento;
}
public String getDireccion() {
	return Direccion;
}
public void setDireccion(String direccion) {
	Direccion = direccion;
}
public Provincia getProvincia() {
	return Provincia;
}
public void setIdProvincia(Provincia idProvincia) {
	Provincia = idProvincia;
}
public Localidad getLocalidad() {
	return Localidad;
}
public void setLocalidad(Localidad idLocalidad) {
	Localidad = idLocalidad;
}
public String getCorreoElectronico() {
	return CorreoElectronico;
}
public void setCorreoElectronico(String correoElectronico) {
	CorreoElectronico = correoElectronico;
}
public int getTelefono() {
	return Telefono;
}
public void setTelefono(int telefono) {
	Telefono = telefono;
}
public Usuarios getUsuarioCli() {
	return UsuarioCli;
}
public void setUsuarioCli(Usuarios usuarioCli) {
	UsuarioCli = usuarioCli;
}
public boolean isEstado() {
	return Estado;
}
public void setEstado(boolean estado) {
	Estado = estado;
}

@Override
public String toString() {
	return "Cliente Dni: " + Dni + ", Cuil: " + Cuil + ", Nombre: " + Nombre + ", Apellido: " + Apellido + ", Sexo: "
			+ Sexo + ", Nacionalidad: " + Nacionalidad + ", FechaDeNacimiento: " + FechaDeNacimiento + ", Direccion: "
			+ Direccion + ", IdProvincia: " + Provincia + ", IdLocalidad: " + Localidad + ", CorreoElectronico: "
			+ CorreoElectronico + ", Telefono: " + Telefono + ", UsuarioCli: " + UsuarioCli + ", Estado: " + Estado
			+ " ";
}

public static boolean compararDni(String dni) throws compararDniException {
	boolean incompleto = false;
	
	if(dni.length()!=8) {
		incompleto = true;
	}
	
	if(incompleto) {
		throw new compararDniException();
	}
	
	return incompleto;
}
	
public static boolean compararCuil(String cuil) throws CompararCuilException {
	boolean incompleto = false;
	
	if(cuil.length()!=11) {
		incompleto = true;
	}
	
	if(incompleto) {
		throw new CompararCuilException();
	}
	return incompleto;
}
	
}
