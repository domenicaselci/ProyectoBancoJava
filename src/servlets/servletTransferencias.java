package servlets;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DaoImple.DaoCuentaImple;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Movimiento;
import Dominio.Provincia;
import Dominio.TipoDeCuenta;
import Dominio.TipoMovimiento;
import NegocioImple.NegocioClienteImple;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioMovimientosImple;
import NegocioImple.NegocioProvinciaImple;

@WebServlet("/servletTransferencias")
public class servletTransferencias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public servletTransferencias() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cargarCuentas(request, response);
		RequestDispatcher rd = request.getRequestDispatcher("/Transferencias.jsp");
        rd.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cargarCuentas(request, response); 
		
		if(request.getParameter("TransferirBtn")!=null) {
			Movimiento mov = new Movimiento();
			Cuenta cu = new Cuenta();
			int cbu=-1;
			
			cu.setCuenta(Integer.parseInt(request.getParameter("CuentaDisponibles")));
			mov.setCuenta(cu);
			mov.setDetalle(request.getParameter("Concepto"));
			
			DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" );
			LocalDate cal = LocalDate.parse(LocalDate.now().toString(),f);
			mov.setFecha(cal);
			
			mov.setImporte(Float.parseFloat(request.getParameter("Importe")));
			
			try {
				cbu=Integer.parseInt(request.getParameter("CBU"));

			} catch (NumberFormatException e) {
		
			}
			cu.setCbu(cbu);
			mov.setCbuDestino(cu);
			
			
			NegocioCuentaImple negCuenta = new NegocioCuentaImple();
			Boolean transfirio = false;
			Boolean existe = false;
				existe = negCuenta.existeCBU(cu);
				if (existe == false) {
				    request.setAttribute("error", "Ese Cbu no le pertenece a ninguna cuenta");
				    RequestDispatcher rd = request.getRequestDispatcher("/Transferencias.jsp");
				    rd.forward(request, response);
				} else {
				    Cuenta cuentaemisora = new Cuenta();
				    cuentaemisora = negCuenta.obtenerporcuenta(cu);
				    
				    if (cu.getCbu() == cuentaemisora.getCbu()) {
				        request.setAttribute("error", "No puedes transferir a tu propia cuenta");
				        RequestDispatcher rd = request.getRequestDispatcher("/Transferencias.jsp");
				        rd.forward(request, response);
				    } else {
				        if (cuentaemisora.getSaldo() >= mov.getImporte()) {
				            NegocioMovimientosImple negImple = new NegocioMovimientosImple();
				            transfirio = negImple.transferencia(mov);
				            request.setAttribute("agrego", transfirio);

				            if (transfirio) {
				                int numeroMovimiento = negImple.ultimatransfer().getMovimiento();
				                mov.setMovimiento(numeroMovimiento-1);
				                HttpSession session = request.getSession();
				                session.setAttribute("ulttrasnfer", mov);
				                RequestDispatcher rd = request.getRequestDispatcher("/DetalleTransferencia.jsp");
				                rd.forward(request, response);
				            } else {
				                cargarCuentas(request, response);
				                RequestDispatcher rd = request.getRequestDispatcher("/Transferencias.jsp");
				                rd.forward(request, response);
				            }
				        } else {
				            request.setAttribute("error", "No tienes el suficiente saldo para transferir");
				            RequestDispatcher rd = request.getRequestDispatcher("/Transferencias.jsp");
				            rd.forward(request, response);
				        }
				    }
				}
		}
	}
	
	protected void cargarCuentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente cli = new Cliente();
		HttpSession session = request.getSession();
		cli = (Cliente) session.getAttribute("cliente");
		Cuenta cuenta = new Cuenta();
		cuenta.setDni(cli);
	    NegocioCuentaImple negCuenta = new NegocioCuentaImple();
	    ArrayList<Cuenta> cuentas = (ArrayList<Cuenta>) negCuenta.obtenerPorDni(cuenta);	
	    request.setAttribute("listaCuentas", cuentas);
		}

}
