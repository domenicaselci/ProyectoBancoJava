package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.TipoMovimiento;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioPrestamoImple;

/**
 * Servlet implementation class servletAutorizarPrestamos
 */
@WebServlet("/servletAutorizarPrestamos")
public class servletAutorizarPrestamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public servletAutorizarPrestamos() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (request.getAttribute("listaPrestamos") == null) {
        NegocioPrestamoImple prestamoNegocio = new NegocioPrestamoImple();
        List <Prestamos> listaSinFiltro=prestamoNegocio.readall();
        List<Prestamos> listaPrestamos = new ArrayList<Prestamos>();
        	for(Prestamos p:listaSinFiltro) {
        		if(p.getEstado()!=4) {
        			listaPrestamos.add(p);
        		}
        	}
        
        
        request.setAttribute("listaPrestamos", listaPrestamos);
    	}
        RequestDispatcher rd = request.getRequestDispatcher("/AutorizarPrestamos.jsp");
        rd.forward(request, response);
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        Prestamos prestamoacambiar = new Prestamos();

        if (request.getParameter("RechazarBtn") != null) {
            NegocioPrestamoImple negPre = new NegocioPrestamoImple();
            try {				
            prestamoacambiar.setPrestamo(Integer.parseInt(request.getParameter("idPrestamo")));           
            prestamoacambiar=negPre.obtenerPorID(prestamoacambiar);
            
            if(prestamoacambiar.getEstado()!=4) {
            	prestamoacambiar.setEstado(3);
            	Boolean cambio=negPre.cambiarEstado(prestamoacambiar);}
			} catch (Exception e) {
				request.setAttribute("formatoBusqueda", false);
			}
        }

        if (request.getParameter("AutorizarBtn") != null) {
            NegocioPrestamoImple negPre = new NegocioPrestamoImple();
            Boolean CambioEstado=false;
            try {	
	            prestamoacambiar.setPrestamo(Integer.parseInt(request.getParameter("idPrestamo")));
	            prestamoacambiar=negPre.obtenerPorID(prestamoacambiar);
	            if(prestamoacambiar.getEstado()!=4) {
		            prestamoacambiar.setEstado(2);
		            CambioEstado=negPre.cambiarEstado(prestamoacambiar);
	            }
	            prestamoacambiar=negPre.obtenerPorID(prestamoacambiar);
			} catch (Exception e) {
				request.setAttribute("formatoBusqueda", false);
			}    
            if(CambioEstado) {
            //Inserción del movimiento de alta de prestamo
            Boolean Acredito=acreditarPrestamo(prestamoacambiar);
            	if(Acredito) {
            		Boolean InsertoMovi=insertarMovimientoAlta(prestamoacambiar);
            	}
            }
            
            
            
        }
        response.sendRedirect(request.getContextPath() + "/servletAutorizarPrestamos");
    }
    
    private boolean insertarMovimientoAlta(Prestamos prestamoacambiar) {
    	Movimiento movAltaPrestamo=new Movimiento();
        NegocioMovimientosImple negMovi=new NegocioMovimientosImple();
        
        TipoMovimiento tpMov=new TipoMovimiento();
        tpMov.setIdTipo(2); ///EL TIPO MOVIMIENTO CON ID 2 CORRESPONDE AL ALTA DE PRESTAMO
        Cuenta cuentaDestino=new Cuenta();
        cuentaDestino.setCbu(-1);///SI EL CBU ES -1, NO ES TRANSFERENCIA
        
        movAltaPrestamo.setCuenta(prestamoacambiar.getCuentaADepositar());
        movAltaPrestamo.setTipoMovimiento(tpMov);
        movAltaPrestamo.setDetalle("Alta de Prestamo N°"+prestamoacambiar.getPrestamo());
        movAltaPrestamo.setFecha(LocalDate.now());
        movAltaPrestamo.setImporte(prestamoacambiar.getImporteSolicitado());
        movAltaPrestamo.setCbuDestino(cuentaDestino);
        
        Boolean InsertoMovi=negMovi.insert(movAltaPrestamo);
        return InsertoMovi;
    }
    private boolean acreditarPrestamo(Prestamos pre) {
    	Boolean acredito=false;
    	NegocioCuentaImple negCuenta=new NegocioCuentaImple();
    	Cuenta cuentaAcreditar=pre.getCuentaADepositar();
    	cuentaAcreditar=negCuenta.obtenerporcuenta(cuentaAcreditar);
    	
    	float saldoNUEVO=cuentaAcreditar.getSaldo()+pre.getImporteSolicitado();
    	
    	cuentaAcreditar.setSaldo(saldoNUEVO);
    	acredito=negCuenta.modificar(cuentaAcreditar);
    	
    	return acredito;
    }
}
