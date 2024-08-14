package Dominio;

import java.time.LocalDate;

public class Usuarios {

	private String Usuario;
	private String contrasena;
	private Boolean Estado;
	private LocalDate FechaCre;
	
	public Usuarios(String usuario, String contrasena, Boolean estado, LocalDate FechaCre) {	
		Usuario = usuario;
		this.contrasena = contrasena;
		Estado = estado;
		this.FechaCre = FechaCre;
	}
	public Usuarios() {}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return Estado;
	}

	public void setEstado(Boolean estado) {
		Estado = estado;
	}
	
	public void setFechaCre(LocalDate fechaCrea) {
		this.FechaCre = fechaCrea;
	}

	public LocalDate getFechaCre() {
		return FechaCre;
	}
	@Override
	public String toString() {
		return "Usuarios Usuario: " + Usuario + ", contrasena: " + contrasena + ", Estado: " + Estado + "";
	}
	
	
	
	
	
}
