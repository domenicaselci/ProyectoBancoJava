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

import Dominio.Prestamos;
import Dominio.Usuarios;
import NegocioImple.NegocioPrestamoImple;
import NegocioImple.NegocioUsuarioImple;

@WebServlet("/servletAdministrarMovimientos")
public class servletAdministrarMovimientos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public servletAdministrarMovimientos() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("btnMostrarPrestamos") != null) {
            NegocioPrestamoImple negPre = new NegocioPrestamoImple();
			Prestamos pre=new Prestamos();
			Prestamos pre2=new Prestamos();
			List<Prestamos> prestamos = new ArrayList<Prestamos>();
			
			try {
			
				DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
				LocalDate ld = LocalDate.parse(request.getParameter("txtFechaIn").toString(), f );
				LocalDate lFin = LocalDate.parse(request.getParameter("txtFechaFin").toString(), f );
				
				pre.setFecha(ld);
				pre2.setFecha(lFin);

				prestamos = negPre.obtenerPorFecha(pre,pre2);
				
			} catch (Exception e) {
				request.setAttribute("CompletarCamposInforme1", "Debe Completar el Campo");
			}

			request.setAttribute("prestamosEnBD",prestamos);
			RequestDispatcher rd = request.getRequestDispatcher("/InformeReportes.jsp");
	        rd.forward(request, response);
        }
		
		if (request.getParameter("btnMostrarUsuarios") != null) {
            NegocioUsuarioImple negUsu = new NegocioUsuarioImple();
			Usuarios usu=new Usuarios();
			Usuarios usuFin=new Usuarios();
			List<Usuarios> usuarios = null;
			try {
				DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
				LocalDate ld = LocalDate.parse(request.getParameter("txtUsuIn").toString(), f );
				LocalDate lFin = LocalDate.parse(request.getParameter("txtUsuFin").toString(), f );
				
				usu.setFechaCre(ld);
				usuFin.setFechaCre(lFin);
	
				usuarios = negUsu.obtenerPorFecha(usu, usuFin);

			} catch (Exception e) {
				request.setAttribute("CompletarCamposInforme2", "Debe Completar el Campo");
			}
			request.setAttribute("usuariosEnBD",usuarios);
			RequestDispatcher rd = request.getRequestDispatcher("/InformeReportes.jsp");
	        rd.forward(request, response);
        }
		
	}

}
