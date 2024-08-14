package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.TipoDeCuenta;
import Dominio.TipoMovimiento;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioTipoDeCuentaImple;
import NegocioImple.NegocioTipoMovimiento;

/**
 * Servlet implementation class servletMenuCliente
 */
@WebServlet("/servletMenuCliente")
public class servletMenuCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletMenuCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/InicioCliente.jsp");
        rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cuenta cue=new Cuenta();
		TipoMovimiento tipoMovimiento=new TipoMovimiento();
		TipoDeCuenta tipoDeCuenta=new TipoDeCuenta();
		ArrayList<Movimiento> movimiento=new ArrayList<Movimiento>();
		NegocioTipoDeCuentaImple negocioTipoDeCuentaImple=new NegocioTipoDeCuentaImple();
		NegocioMovimientosImple negocioMovimientosImple=new NegocioMovimientosImple();
		NegocioTipoMovimiento negocioTipoMovimiento=new NegocioTipoMovimiento();
		
		
		HttpSession session = request.getSession();
		ArrayList<Cuenta> Lcuenta = (ArrayList<Cuenta>)session.getAttribute("cuentas");
		NegocioCuentaImple negocioCuentaImple=new NegocioCuentaImple();
		
		int c=1;
		
		for(int i=0;i<3;i++) {
			
		if(request.getParameter("btnCuenta"+c)!=null) {
			
			cue=Lcuenta.get(i);
			cue=negocioCuentaImple.obtenerporcuenta(cue);
			
			tipoDeCuenta=cue.getTipoDeCuenta();
			tipoDeCuenta=negocioTipoDeCuentaImple.obtenerTipoPorID(tipoDeCuenta);
			cue.setTipoDeCuenta(tipoDeCuenta);
			movimiento=((ArrayList<Movimiento>) negocioMovimientosImple.ObtenerMovimientosporCuenta(cue));
			for(Movimiento a:movimiento) {
				tipoMovimiento=a.getTipoMovimiento();
				tipoMovimiento=negocioTipoMovimiento.obtenerPorMov(tipoMovimiento);
				a.setTipoMovimiento(tipoMovimiento);
				
			}
			
			
		session.setAttribute("cuenta", cue);
		session.setAttribute("movimiento", movimiento);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Movimientos.jsp");
        rd.forward(request, response);	
        
		}
		c=c+1;
		}
		
		
		
		
		
		
	
		
	}
	


}
