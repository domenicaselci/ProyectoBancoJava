package Dominio;

public class TipoMovimiento {

	private int IdTipo;
	private String Descripcion;
	
	
	public TipoMovimiento(int idTipo, String descripcion) {
		
		IdTipo = idTipo;
		Descripcion = descripcion;
	}
	
	public TipoMovimiento(){
		
	}
	
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
		return "TipoMovimiento IdTipo: " + IdTipo + ", Descripcion: " + Descripcion + "";
	}

	
}
