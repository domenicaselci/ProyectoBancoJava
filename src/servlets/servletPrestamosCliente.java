package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
import com.sun.javafx.collections.FloatArraySyncer;

import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.Provincia;
import Dominio.TipoMovimiento;
import Excepciones.CompararCuotasException;
import Excepciones.SaldoNegativoException;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioPrestamoImple;
import NegocioImple.NegocioProvinciaImple;
import jdk.internal.dynalink.beans.StaticClass;

/**
 * Servlet implementation class servletPrestamosCliente
 */
@WebServlet("/servletPrestamosCliente")
public class servletPrestamosCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
    public servletPrestamosCliente() {
       
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		if(request.getParameter("btnSolicitarPrestamo")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/SolicitarPrestamo.jsp");
			rd.forward(request, response);
	    }else if(request.getParameter("btnPagarPrestamo")!=null) {
			cargarCuotas(request, response);
			cargarCuentas(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/PagarPrestamo.jsp");
			rd.forward(request, response);
	    }else if(request.getParameter("btnGestionarPrestamo")!=null) {
	    	/// OBTENER PRESTAMOS 
			
			HttpSession session1 = request.getSession(false);
			Cliente cliente1= new Cliente();
			cliente1 = (Cliente) session1.getAttribute("cliente");		
		
			 Prestamos pre2 = new Prestamos();
			 pre2.setDni(cliente1);
			
			
			NegocioPrestamoImple neg = new NegocioPrestamoImple ();
			ArrayList<Prestamos> prestamosList = new ArrayList<>();
			prestamosList = (ArrayList<Prestamos>) neg.obtenerPorDni(pre2);
			
			request.setAttribute("prestamosList", prestamosList);
			
			cargarEstadosPrestamos(request, response, pre2, "Aprobados", 2);
			cargarEstadosPrestamos(request, response, pre2, "Rechazados", 3);
			cargarEstadosPrestamos(request, response, pre2, "Pendientes", 1);
			cargarEstadosPrestamos(request, response, pre2, "Pagos", 4);
			
	        //-------------------------------------------------
	 
	    	RequestDispatcher rd = request.getRequestDispatcher("/GestorPrestamos.jsp");
			rd.forward(request, response);
	    }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		///SOLICITAR PRESTAMO
		//---> Calcular el prestamo
		if(request.getParameter("btnCalcularPrestamo")!=null) {
			if(request.getParameter("btnCalcularPrestamo")!=null) {
	            final float INTERES=(float) 1.65;

	            Prestamos prestSolicitar=new Prestamos();
	            Cliente cli=(Cliente)session.getAttribute("cliente");
	            try {
					float importe=Float.parseFloat(request.getParameter("ImporteSolicitado"));
					int cuotas=Integer.parseInt(request.getParameter("sPlazo"));
					float ImporteIntereses=redondear2decimales(calcularImporteInteres(INTERES, importe));
		            float MontoCI=redondear2decimales(ImporteIntereses/cuotas);
		            int cuenta=Integer.parseInt(request.getParameter("opcuentas"));
		            Cuenta cuentaDepo=new Cuenta();
		            cuentaDepo.setCuenta(cuenta);


		            prestSolicitar.setDni(cli);
		            prestSolicitar.setImporteSolicitado(importe);
		            prestSolicitar.setCuotas(cuotas);
		            prestSolicitar.setImporteConInteres(ImporteIntereses);
		            prestSolicitar.setMontoCuotas(MontoCI);
		            prestSolicitar.setPlazo(cuotas);
		            prestSolicitar.setCuentaADepositar(cuentaDepo);
		            request.setAttribute("cuentaDepositar", cuenta);

				} catch (NumberFormatException e) {
					request.setAttribute("CompletarCampo", "Debe completar campos");
				}            
	      
	            session.setAttribute("prestSolicitar", prestSolicitar);
	            

	            RequestDispatcher rd = request.getRequestDispatcher("/SolicitarPrestamo.jsp");
	            rd.forward(request, response);
	        }
		}
		//---> Pedir Prestamo
		if(request.getParameter("btnPedirPrestamo")!=null) {
			if(session.getAttribute("prestSolicitar")!=null) {
				Prestamos prestamoSolicitar=(Prestamos)session.getAttribute("prestSolicitar");
				prestamoSolicitar.setFecha(LocalDate.now());
				prestamoSolicitar.setEstado(1);
				NegocioPrestamoImple negPrest= new NegocioPrestamoImple();
				Boolean inserto=negPrest.insert(prestamoSolicitar);
				request.setAttribute("pedido", inserto);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/SolicitarPrestamo.jsp");
	        rd.forward(request, response);
		}
			
		/// ---> PAGAR PRESTAMO
		if(request.getParameter("btnPagar")!=null) {
			NegocioPrestamoImple negPres= new NegocioPrestamoImple();
			Cuenta cu = new Cuenta();
			Boolean cue = false;
			Cuenta cuen = new Cuenta();
			Cuenta ccc = new Cuenta();
			NegocioCuentaImple negCuenta = new NegocioCuentaImple();
			Prestamos pres = new Prestamos();
			Prestamos presta = new Prestamos();
			Prestamos prestamos = new Prestamos();
			boolean modifico = false;
			boolean mayor = false;
			boolean negativo = false;
			boolean nuevo = false;
			
			try {
				
			pres.setPrestamo(Integer.parseInt(request.getParameter("Prestamo")));
			presta = negPres.obtenerPorPrestamo(pres);
			cu.setCuenta(Integer.parseInt(request.getParameter("Cuenta")));
			cu.setSaldo(presta.getMontoCuotas());
			ccc = negCuenta.obtenerporcuenta(cu);

			} catch (NumberFormatException e) {
				request.setAttribute("SinPrestamos", "No tiene Prestamos");
			}
			
			try {
			    mayor = prestamos.compararCuotas(presta);
			    negativo = cuen.saldoNegativo(presta,ccc);
			} catch (CompararCuotasException e) {
			    mayor = true; 
			} catch(SaldoNegativoException i) {
				negativo = true;
			}
			
			if(negativo) {
				nuevo=true;
			}
			if(mayor || negativo) {
				modifico=false;
			}
			if(mayor) {
				pres.setEstado(4);
				negPres.cambiarEstado(pres);
			}
			if(!mayor && !negativo) {
				modifico=negPres.modificarCuota(pres);
				cue = negCuenta.modificarSaldo(cu);
				if(cue) {
					Boolean insertoMov=insertarMovimientoPago(presta,cu); //REVISAR QUE DETALLE EN BD SEA VARCHAR MAS DE 25
				}
			}
			
			request.setAttribute("modifico", modifico);
			request.setAttribute("presta", presta);
			request.setAttribute("resto", cue);
			request.setAttribute("nuevo", nuevo);
			
			cargarCuotas(request, response);
			cargarCuentas(request, response);
			
			RequestDispatcher rd = request.getRequestDispatcher("/PagarPrestamo.jsp");
	        rd.forward(request, response);
		}
	}

	
	protected void cargarCuotas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente cli = new Cliente();
		Cliente cliente = new Cliente();
		Prestamos pres = new Prestamos();
		List<Prestamos> presDni = new ArrayList<Prestamos>();
		List<Prestamos> presDniActivos = new ArrayList<Prestamos>();
		NegocioPrestamoImple negPres= new NegocioPrestamoImple();
		
		HttpSession session = request.getSession();
		cliente = (Cliente) session.getAttribute("cliente");
		cli.setDni(cliente.getDni());
		pres.setDni(cli);
		
		presDni = negPres.obtenerPorDni(pres);

		for(Prestamos p:presDni) {
			if(p.getEstado()==2) {
				presDniActivos.add(p);
			}
		}
				
		request.setAttribute("listaprestamos", presDniActivos);
   
	}
	
	protected void cargarCuentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cuenta cu = new Cuenta();
		Cliente cli = new Cliente();
		Cliente cliente = new Cliente();
		List<Cuenta> cuentaDni = new ArrayList<Cuenta>();
		NegocioCuentaImple negCu= new NegocioCuentaImple();
		
		HttpSession session = request.getSession();
		cliente = (Cliente) session.getAttribute("cliente");
		cli.setDni(cliente.getDni());
		cu.setDni(cli);
		
		cuentaDni = negCu.obtenerPorDni(cu);
		request.setAttribute("listacuentas", cuentaDni);
 
	}
	
	public float calcularImporteInteres(float Intereses,float Prestamo) {
		float importeI=0;
		importeI=Intereses*Prestamo;
		return importeI;
	}
	public float redondear2decimales(float nro) {
		float redondeado;
		redondeado=nro*100;
		redondeado=Math.round(redondeado);
		redondeado=redondeado/100;
		return redondeado;
		
	}
	private boolean insertarMovimientoPago(Prestamos prestamoacambiar,Cuenta cuentaPago) {
    	Movimiento movPagoPrestamo=new Movimiento();
        NegocioMovimientosImple negMovi=new NegocioMovimientosImple();
        
        TipoMovimiento tpMov=new TipoMovimiento();
        tpMov.setIdTipo(3); ///EL TIPO MOVIMIENTO CON ID 3 CORRESPONDE AL PAGO DE PRESTAMO
        Cuenta cuentaDestino=new Cuenta();
        cuentaDestino.setCbu(-1);///SI EL CBU ES -1, NO ES TRANSFERENCIA
        
        movPagoPrestamo.setCuenta(cuentaPago);
        movPagoPrestamo.setTipoMovimiento(tpMov);
        prestamoacambiar.setCuotasPagadas(prestamoacambiar.getCuotasPagadas()+1);
        movPagoPrestamo.setDetalle("Pago de Cuota N° "+prestamoacambiar.getCuotasPagadas()+" de PRESTAMO N° "+prestamoacambiar.getPrestamo());
        movPagoPrestamo.setFecha(LocalDate.now());
        movPagoPrestamo.setImporte(-prestamoacambiar.getMontoCuotas());
        movPagoPrestamo.setCbuDestino(cuentaDestino);
        
        Boolean InsertoMovi=negMovi.insert(movPagoPrestamo);
        return InsertoMovi;
    }
	protected void cargarEstadosPrestamos(HttpServletRequest request, HttpServletResponse response,Prestamos pre,String nombreEstado,int Estado) throws ServletException, IOException {
	    NegocioPrestamoImple negPre=new NegocioPrestamoImple();
	    ArrayList<Prestamos> prestamosList = new ArrayList<>();
		prestamosList = (ArrayList<Prestamos>) negPre.obtenerPorDni(pre);
		
		int c=0;
		for(Prestamos p:prestamosList) {
			if(p.getEstado()==Estado) {c++;}
		}
		request.setAttribute(nombreEstado, c);
   
	}
	
}
