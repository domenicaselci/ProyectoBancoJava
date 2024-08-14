package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Localidad;
import Dominio.Movimiento;
import Dominio.Prestamos;
import Dominio.Provincia;
import Dominio.TipoDeCuenta;
import Dominio.TipoMovimiento;
import Dominio.Usuarios;
import Excepciones.CompararCuilException;
import Excepciones.compararDniException;
import NegocioImple.NegocioClienteImple;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioLocalidadImple;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioTipoDeCuentaImple;
import NegocioImple.NegocioUsuarioImple;
import jdk.nashorn.internal.runtime.ListAdapter;

@WebServlet("/servletAdministrarCuentas")
public class servletAdministrarCuentas extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

    public servletAdministrarCuentas() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnDarAltaCuenta")!=null) {
			cargarNroDeCuenta(request, response); 
			cargarTiposDeCuentas(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/AltaDeCuenta.jsp");
	        rd.forward(request, response);
		}
		if(request.getParameter("btnDarBajaCuenta")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");   
	        rd.forward(request, response);
		}
		if(request.getParameter("btnModificarCuenta")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ModificarCuenta.jsp");   
	        rd.forward(request, response);
		}	
		if(request.getParameter("btnListarCuenta")!=null) {
			cargarTodasCuentasActivas(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");   
	        rd.forward(request, response);
		}	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnDarAlta")!=null) {
			NegocioCuentaImple NegCuenta=new NegocioCuentaImple();
			Cuenta cU=new Cuenta();
			Cliente cli = new Cliente();
			TipoDeCuenta tcuenta = new TipoDeCuenta();
			
			Boolean inserto=false;
			Boolean contar=false;
	        
			cU.setCuenta(Integer.parseInt(request.getParameter("hNroCuenta")));
			cU.setCbu(Integer.parseInt(request.getParameter("hCbu")));
			
			Boolean dni=false;
			try{
				cli.compararDni(request.getParameter("txtDNI"));}
			catch (compararDniException e) {
				dni = true; 
				request.setAttribute("dni", dni);
			}
			
			if(!dni) {cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));}
			cU.setDni(cli);
			
			cU.setFechadeCreacion(LocalDate.now());
			
			tcuenta.setIdTipo(Integer.parseInt(request.getParameter("TipoDeCuenta")));			
			cU.setTipoDeCuenta(tcuenta);
			
			cU.setSaldo(10000); //SALDO FIJO DE ALTA
			cU.setEstado(true);
			int entroContar=0;
			if(!dni) {contar= NegCuenta.contarCuentas(cU);entroContar++;}
			
			if(contar) {
				
				inserto=NegCuenta.insert(cU);
				
				//FUNCION PARA REGISTRAR MOVIMIENTO Y ACREDITAR LOS 10.000 INICIALES
				if(inserto) {
					
					Boolean acredito=acreditarSaldoCuenta(cU);
					if(acredito) {
						Boolean registroMov=registrarMovimientoAlta(cU);
						request.setAttribute("registroMov", registroMov);
					}
				}				
				
			}else if(entroContar!=0) {
				request.setAttribute("limiteCuentas", contar);
			}
	        request.setAttribute("agrego", inserto);
	        
	        cargarNroDeCuenta(request, response);
	        cargarTiposDeCuentas(request, response);
	        RequestDispatcher rd = request.getRequestDispatcher("/AltaDeCuenta.jsp");
	        rd.forward(request, response);
			
		}
			
		
		//modificar
		Cuenta cuentaModificar=new Cuenta();
		TipoDeCuenta tcuenta = new TipoDeCuenta();
		if(request.getParameter("btnBuscarModificar")!=null) {
			NegocioCuentaImple negCue= new NegocioCuentaImple();
			Cuenta cue = new Cuenta();
			NegocioClienteImple ncli=new NegocioClienteImple();
			
			List<Cliente> cliente=ncli.readAll();
			
			try{
				cue.setCuenta(Integer.parseInt(request.getParameter("txtNumeroDeCuenta")));
			}catch(NumberFormatException e){
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
			}
			Boolean existe=negCue.existeCuenta(cue);
			if(existe) {
				cuentaModificar = negCue.obtenerporcuenta(cue);
				for(Cliente cl:cliente) {
					if(cuentaModificar.getDni().getDni()==(cl.getDni())) {
						cuentaModificar.setDni(cl);
					}
				}
				
			}
		
			request.setAttribute("existe",existe);
			request.setAttribute("cuentaModificar",cuentaModificar);
			cargarTiposDeCuentas(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/ModificarCuenta.jsp");
	        rd.forward(request, response);
			
	}
		
		
		if(request.getParameter("btnModificar")!=null) {
			NegocioClienteImple NegCliente=new NegocioClienteImple();
			NegocioCuentaImple nCuentaImple=new NegocioCuentaImple();
			Cuenta cu=new Cuenta();
			Cliente cli=new Cliente();			
			
			Boolean modifico=false;
			Boolean dni=false;
			try {
			    cli.compararDni(request.getParameter("txtDni"));
			} catch (compararDniException e) {
			    dni = true; 
			} 
						
			if(!dni) {
				cli.setDni(Integer.parseInt(request.getParameter("txtDni")));
			}	
			int cVacioCampo=0; //contador para ver si entró al catch.
			
			try {
				
				cu.setCuenta(Integer.parseInt(request.getParameter("hCuenta")));
				cu.setCbu(Integer.parseInt(request.getParameter("txtCbu")));			
				
				cu.setDni(cli);
				
				cu.setFechadeCreacion(LocalDate.now());
				
				tcuenta.setIdTipo(Integer.parseInt(request.getParameter("TiposCuenta")));			
				cu.setTipoDeCuenta(tcuenta);
				
				cu.setSaldo(Float.parseFloat(request.getParameter("txtSaldo")));
				cu.setEstado(Boolean.parseBoolean(request.getParameter("rbEstado")));
				
				DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" );
				LocalDate ld = LocalDate.parse( request.getParameter("txtFechaCreacion").toString(), f );
				cu.setFechadeCreacion(ld);
			} catch (NumberFormatException e) {
				request.setAttribute("CompletarCamposAModificar", true);
				cVacioCampo++;
			}	        		
	        if((dni==false)&&(cVacioCampo==0)) {modifico=nCuentaImple.modificar(cu);
	        request.setAttribute("modifico", modifico);}
	        
	        
	        request.setAttribute("dni", dni);
	        RequestDispatcher rd = request.getRequestDispatcher("/ModificarCuenta.jsp");
	        rd.forward(request, response);
			
			
		}
		if(request.getParameter("btnBuscarEliminar")!=null) {
			
			NegocioCuentaImple nCuentaImple=new NegocioCuentaImple();
			Cuenta cuenta=new Cuenta();
			Cuenta cuentaEli = new Cuenta();
			int entroCatch=0;
			
			try {
				cuenta.setCuenta(Integer.parseInt(request.getParameter("txtNumeroDeCuenta")));
			} catch (NumberFormatException e) {
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				entroCatch++;
			}
			
			
			
			if(nCuentaImple.existeCuenta(cuenta)==true) {			
				cuentaEli = nCuentaImple.obtenerporcuenta(cuenta);
				request.setAttribute("Cuentaeli",cuentaEli);
			}else if(entroCatch==0) {
				request.setAttribute("cuentaNoExiste"," No Existe");
			}
			

			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");
	        rd.forward(request, response);
		}
		if(request.getParameter("btnEliminar")!=null) {
			NegocioCuentaImple nCuentaImple=new NegocioCuentaImple();
			Boolean elimino=false;
			Cuenta cuentaEli = new Cuenta();
			
			cuentaEli.setCuenta(Integer.parseInt(request.getParameter("hCuenta")));
			elimino=nCuentaImple.BajaLogica(cuentaEli);
			
			request.setAttribute("elimino",elimino);
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");
	        rd.forward(request, response);
		}	
		
		/// Listar
		if(request.getParameter("btnMostrar")!=null) {
			cargarTodasCuentasActivas(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");
	        rd.forward(request, response);

		}
		
		if(request.getParameter("BTNBuscarCuenta")!=null) {
			NegocioCuentaImple nCuentaImple=new NegocioCuentaImple();
			Boolean existe=false;
			Cuenta cuentaBuscar = new Cuenta();
			Cuenta cuentaObtenida=new Cuenta();
			
			try{cuentaBuscar.setCuenta(Integer.parseInt(request.getParameter("txtBuscarCuenta")));
			}catch(NumberFormatException e){
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				
			}
			existe=nCuentaImple.existeCuenta(cuentaBuscar);
			
			if(existe) {
				cuentaObtenida=nCuentaImple.obtenerporcuenta(cuentaBuscar);
				if(cuentaObtenida.isEstado()==true) {
				cuentaObtenida=cargarClienteCompleto(cuentaObtenida);
				request.setAttribute("cuentaObtenida", cuentaObtenida);
				existe=false;
				}
			}
			
			request.setAttribute("existe",existe);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");
	        rd.forward(request, response);
		}
		
		if(request.getParameter("BTNBuscarDni")!=null) {
			NegocioCuentaImple negCu = new NegocioCuentaImple();
			Cuenta cue=new Cuenta();
			Cliente cli=new Cliente();
			int entroCatch=0;
			
			try{cli.setDni(Integer.parseInt(request.getParameter("txtBuscarDni")));
			cue.setDni(cli);
			}catch(NumberFormatException e){
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				entroCatch++;
			}
			
			List<Cuenta> cuentasvalidas = new ArrayList<Cuenta>();
			if(entroCatch==0) {
				List<Cuenta> cuentas=negCu.obtenerPorDni(cue);
				for(Cuenta cu:cuentas) {
					if(cu.isEstado()==true) {
					cu=cargarClienteCompleto(cu);
					cuentasvalidas.add(cu);
					}
				}
			}
			request.setAttribute("cuentaEnBD",cuentasvalidas);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");
	        rd.forward(request, response);

		}
		
		
		if(request.getParameter("BTNFiltrar")!=null) {
			NegocioCuentaImple negCu = new NegocioCuentaImple();
			NegocioTipoDeCuentaImple negocioTipoDeCuentaImple=new NegocioTipoDeCuentaImple();
			float montoIni;
			float montoFin;
			TipoDeCuenta tipoDeCuenta=new TipoDeCuenta();
			List<Cuenta> lcCuentas=new ArrayList<Cuenta>();
			
			try {
				montoIni=(Float.parseFloat(request.getParameter("txtmontoIni")));
				montoFin=(Float.parseFloat(request.getParameter("txtMontoFin")));
				lcCuentas=negCu.ObtenerFiltroCuenta(montoIni, montoFin);
			} catch (NumberFormatException e) {
				request.setAttribute("CompletarCampoFiltro", "Debe Completar el Campo");
			}
			
			
			for(Cuenta obj:lcCuentas) {
				tipoDeCuenta.setIdTipo(obj.getTipoDeCuenta().getIdTipo());
				tipoDeCuenta=negocioTipoDeCuentaImple.obtenerTipoPorID(tipoDeCuenta);
				obj.setTipoDeCuenta(tipoDeCuenta);
				
				
			}
		
			request.setAttribute("ListacuentaFiltro",lcCuentas);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");
	        rd.forward(request, response);
			
			
		}
		
		if(request.getParameter("BTNFiltrarFecha")!=null) {
			
			NegocioCuentaImple negCu = new NegocioCuentaImple();
			NegocioTipoDeCuentaImple negocioTipoDeCuentaImple=new NegocioTipoDeCuentaImple();
			List<Cuenta> lcCuentas=new ArrayList<Cuenta>();
			TipoDeCuenta tipoDeCuenta=new TipoDeCuenta();
			DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" );
			
			try {
				LocalDate ld = LocalDate.parse( request.getParameter("txtFechaIni").toString(), f );
				LocalDate ld2= LocalDate.parse( request.getParameter("txtFechaFin").toString(), f );
				lcCuentas=negCu.ObtenerFiltroFecha(ld, ld2);
			} catch (DateTimeParseException e) {
				request.setAttribute("CompletarCampoFiltro", "Debe Completar el Campo");
			}
			
			for(Cuenta obj:lcCuentas) {
				tipoDeCuenta.setIdTipo(obj.getTipoDeCuenta().getIdTipo());
				tipoDeCuenta=negocioTipoDeCuentaImple.obtenerTipoPorID(tipoDeCuenta);
				obj.setTipoDeCuenta(tipoDeCuenta);

			}
			request.setAttribute("ListaFiltroFechas",lcCuentas);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");
	        rd.forward(request, response);
			
		}
		
		
		
	}
	
	public Cuenta cargarClienteCompleto(Cuenta cu) {
		NegocioClienteImple negCli=new NegocioClienteImple();
		NegocioTipoDeCuentaImple negTdc=new NegocioTipoDeCuentaImple();
		
		List<Cliente> clientes=negCli.readAll();
		List<TipoDeCuenta> tdCuentas=negTdc.readAll();	
		
		for(Cliente cli:clientes) {
			if(cu.getDni().equals(cli.getDni())) {
				cu.setDni(cli);
			}
		}
		for(TipoDeCuenta t:tdCuentas) {
			if(cu.getTipoDeCuenta().getIdTipo()==t.getIdTipo()) {
				cu.setTipoDeCuenta(t);
			}
		}
		
		return cu;
	}
	
	private void cargarTiposDeCuentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    NegocioTipoDeCuentaImple negTDC = new NegocioTipoDeCuentaImple();
	    ArrayList<TipoDeCuenta> tiposDeCuentas = (ArrayList<TipoDeCuenta>) negTDC.readAll();

	    request.setAttribute("listaTDC", tiposDeCuentas);
	    
   
	}
	
	private boolean registrarMovimientoAlta(Cuenta cuentaNueva) {
		Boolean registro=false;
		
		Movimiento movAltaCuenta=new Movimiento();
        NegocioMovimientosImple negMovi=new NegocioMovimientosImple();
        
        TipoMovimiento tpMov=new TipoMovimiento();
        tpMov.setIdTipo(1); ///EL TIPO MOVIMIENTO CON ID 1 CORRESPONDE AL ALTA DE CUENTA
        Cuenta cuentaDestino=new Cuenta();
        cuentaDestino.setCbu(-1);///SI EL CBU ES -1, NO ES TRANSFERENCIA
        
        movAltaCuenta.setCuenta(cuentaNueva);
        movAltaCuenta.setTipoMovimiento(tpMov);
        movAltaCuenta.setDetalle("ALTA DE CUENTA");
        movAltaCuenta.setFecha(LocalDate.now());
        movAltaCuenta.setImporte(cuentaNueva.getSaldo());
        movAltaCuenta.setCbuDestino(cuentaDestino);
        
        registro=negMovi.insert(movAltaCuenta);
		
		return registro;
	}
	public boolean acreditarSaldoCuenta(Cuenta cuentaNueva) {
    	Boolean acredito=false;
    	NegocioCuentaImple negCuenta=new NegocioCuentaImple();
    	acredito=negCuenta.modificar(cuentaNueva);
    	
    	return acredito;
    }
	
	private void cargarNroDeCuenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    NegocioCuentaImple negCuenta = new NegocioCuentaImple();
	    ArrayList<Cuenta> cuentas = (ArrayList<Cuenta>) negCuenta.readAll();
	    int nroProximo=0;
	    for(Cuenta c:cuentas) {
	    	nroProximo=c.getCuenta();
	    }
	    nroProximo=nroProximo+1;
	    	    
	    request.setAttribute("nroCuenta", nroProximo);
	    
   
	}
	private void cargarTodasCuentasActivas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NegocioCuentaImple negCu = new NegocioCuentaImple();
		
		List<Cuenta> cuentas=negCu.readAll();	
		List<Cuenta> cuentasvalidas = new ArrayList<Cuenta>();
		
		for(Cuenta cu:cuentas) {
			if(cu.isEstado()==true) {
			cu=cargarClienteCompleto(cu);
			cuentasvalidas.add(cu);
			}
		}
		
		request.setAttribute("cuentaEnBD",cuentasvalidas);

	}
}