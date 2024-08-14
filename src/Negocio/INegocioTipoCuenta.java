package Negocio;

import java.util.List;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import Dominio.TipoDeCuenta;

public interface INegocioTipoCuenta {
	public boolean insert(TipoDeCuenta tdC);
	public boolean modificar(TipoDeCuenta tdC);
	public boolean bajalogica(TipoDeCuenta tdC);
	public List<TipoDeCuenta> readAll();
	public TipoDeCuenta obtenerTipoPorID(TipoDeCuenta tdC);
}
