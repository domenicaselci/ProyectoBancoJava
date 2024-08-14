package servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Usuarios;
import NegocioImple.NegocioClienteImple;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioUsuarioImple;

/**
 * Servlet implementation class servletLogin
 */
@WebServlet("/servletLogin")
public class servletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public servletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String usuario = request.getParameter("txtUsuario");
        String contrasena = request.getParameter("txtContrasena");
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate cal = LocalDate.parse(LocalDate.now().toString(),f);
        
        Usuarios usu = new Usuarios(usuario,contrasena,true,cal);
        NegocioUsuarioImple neoU = new NegocioUsuarioImple();
        
        
        if ("admin".equals(usuario) && "admin".equals(contrasena)) {
            
            RequestDispatcher rd = request.getRequestDispatcher("MenuAdmin.jsp");
	        rd.forward(request, response);
        } 
        else {
        	if(neoU.existeUsuario(usu)) {
        		Usuarios usuNuevo = new Usuarios();
        		usuNuevo = neoU.obtenerporusu(usu);
        		if(usuNuevo.getEstado()) {
        		if(contrasena.equals(usuNuevo.getContrasena())) {
        			Cliente cli = new Cliente();
        			Cuenta cuenta=new Cuenta();
        			NegocioClienteImple nCli = new NegocioClienteImple();
        			NegocioCuentaImple negocioCuentaImple=new NegocioCuentaImple();
        			
        			cli = nCli.obtenerPorusuario(usu);
        			cuenta.setDni(cli);
        			ArrayList<Cuenta> Listacuenta=(ArrayList<Cuenta>)negocioCuentaImple.obtenerPorDni(cuenta);
        			HttpSession session = request.getSession();
        			
        			ArrayList<Cuenta> ListacuentaFiltrada=new ArrayList<Cuenta>();
        			for(Cuenta c:Listacuenta) {
        				if(c.isEstado()) {
        					ListacuentaFiltrada.add(c);
        				}
        			}
        			
                    session.setAttribute("cliente", cli);
                    session.setAttribute("cuentas", ListacuentaFiltrada);
                    RequestDispatcher rd = request.getRequestDispatcher("/InicioCliente.jsp");
        	        rd.forward(request, response);
        		}else { request.setAttribute("error", "Contraseña Inválida");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
                dispatcher.forward(request, response);
                }
        	}else {
        		request.setAttribute("error", "Usuario Inhabilitado");
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
	            dispatcher.forward(request, response);
        	}}
        	else { request.setAttribute("error", "Usuario Inexistente");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
            dispatcher.forward(request, response);
            }
		    
           
        }
       
        
    }
}

