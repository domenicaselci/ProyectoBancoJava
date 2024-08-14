package Dominio;

public class TipoDeCuenta {

	private int IdTipo;
	private String Descripcion;
	
	public TipoDeCuenta(int idTipo, String descripcion) {
	
		IdTipo = idTipo;
		Descripcion = descripcion;
	}
	public TipoDeCuenta() {}

	public int getIdTipo() {
		return IdTipo;
	}

	public void setIdTipo(int idTipo) {
		IdTipo = idTipo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoDeCuenta IdTipo: " + IdTipo + ", Descripcion: " + Descripcion + "";
	}
	
	
}
