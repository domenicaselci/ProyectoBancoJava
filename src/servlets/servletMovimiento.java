package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.accessibility.internal.resources.accessibility;

import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.TipoMovimiento;
import Dominio.Usuarios;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioTipoDeCuentaImple;
import NegocioImple.NegocioTipoMovimiento;

/**
 * Servlet implementation class servletMovimiento
 */
@WebServlet("/servletMovimiento")
public class servletMovimiento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletMovimiento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnAplicarFiltro")!=null) {
			
		Cuenta cuenta=new Cuenta();
		ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
		NegocioMovimientosImple negocioMovimientosImple=new NegocioMovimientosImple();
		cuenta.setCuenta(Integer.parseInt(request.getParameter("cuenta")));
		
		try {
			float MontoIni =Float.parseFloat(request.getParameter("InicioMonto"));
			float MontoFin = Float.parseFloat(request.getParameter("FinalMonto"));
			lMovimientos=(ArrayList<Movimiento>)negocioMovimientosImple.ObtenerMovimientosporFiltro(MontoIni, MontoFin,cuenta);

		} catch (NumberFormatException e) {
			request.setAttribute("CompletarFiltros", "Debe completar ambos filtros");
		}

		
		
		request.setAttribute("MovimientoFiltro",lMovimientos);
		RequestDispatcher rd = request.getRequestDispatcher("/Movimientos.jsp");
        rd.forward(request, response);
		}
		
		if(request.getParameter("btnAplicarFiltroFechas")!=null) {
			Cuenta cuenta=new Cuenta();
			ArrayList<Movimiento> lMovimientos=new ArrayList<Movimiento>();
			NegocioMovimientosImple negocioMovimientosImple=new NegocioMovimientosImple();
			cuenta.setCuenta(Integer.parseInt(request.getParameter("cuenta")));
			
			try {				
				DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" );
				LocalDate ld = LocalDate.parse( request.getParameter("InicioFecha").toString(), f );
				LocalDate ld2= LocalDate.parse( request.getParameter("FinalFecha").toString(), f );
				lMovimientos=(ArrayList<Movimiento>)negocioMovimientosImple.ObtenerFiltroFechaMovimiento(ld, ld2, cuenta);
			} catch (Exception e) {
				request.setAttribute("CompletarFiltros", "Debe completar ambos filtros");
			}
			
		
			request.setAttribute("MovimientoFiltroFechas",lMovimientos);
			RequestDispatcher rd = request.getRequestDispatcher("/Movimientos.jsp");
	        rd.forward(request, response);
		}
		
		
		if(request.getParameter("btnMostarTodos")!=null) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/Movimientos.jsp");
			rd.forward(request, response);
		}
		
		
		if(request.getParameter("BtnDetalleLike")!=null) {
			
			
			int numeroCuenta=(Integer.parseInt(request.getParameter("cuenta")));
			String Texto=(request.getParameter("DetalleLike"));
			
			List<Movimiento> lMovimientos=new ArrayList<Movimiento>();
			
			NegocioMovimientosImple negocioMovimientosImple=new NegocioMovimientosImple();
			
			lMovimientos=negocioMovimientosImple.obtenerMovimientoLike(Texto, numeroCuenta);
	
			request.setAttribute("MovimientoLikeDetalle",lMovimientos);
			RequestDispatcher rd = request.getRequestDispatcher("/Movimientos.jsp");
	        rd.forward(request, response);
			
			
		}
		
		
	}

}