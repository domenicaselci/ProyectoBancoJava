package Dominio;

public class Provincia {

	private int IdProvincia;
	private String Nombre;
	
	public Provincia(int idProvincia, String nombre) {

		IdProvincia = idProvincia;
		Nombre = nombre;
	}
	public Provincia () {
		
	}

	public int getIdProvincia() {
		return IdProvincia;
	}

	public void setIdProvincia(int idProvincia) {
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
		return "Provincia IdProvincia: " + IdProvincia + ", Nombre: " + Nombre + "";
	}
	
	
}
