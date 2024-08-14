package Dominio;

public class Localidad {

	private int IdLocalidad;
	private Provincia IdProvincia;
	private String Nombre;
	
	public Localidad(int idLocalidad, Provincia idProvincia, String nombre) {
	
		IdLocalidad = idLocalidad;
		IdProvincia = idProvincia;
		Nombre = nombre;
	}
	
	public Localidad() {}

	public int getIdLocalidad() {
		return IdLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		IdLocalidad = idLocalidad;
	}

	public Provincia getIdProvincia() {
		return IdProvincia;
	}

	public void setIdProvincia(Provincia idProvincia) {
		IdProvincia = idProvincia;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	@Override
	public String toString() {
		return "Localidad IdLocalidad: " + IdLocalidad + ", IdProvincia: " + IdProvincia + ", Nombre: " + Nombre + "";
	}
	
	
	
	
	
}
