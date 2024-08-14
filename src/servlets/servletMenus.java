package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servletMenus")
public class servletMenus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public servletMenus() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("btnAdministarClientes")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/AdministrarCliente.jsp");   
	        rd.forward(request, response);
		}
		if(request.getParameter("btnAdministarCuentas")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/AdministrarCuentas.jsp");   
	        rd.forward(request, response);
		}
		if(request.getParameter("btnAutorizarPrestamos")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("servletAutorizarPrestamos?Param=1");   
	        rd.forward(request, response);
		}	
		if(request.getParameter("btnInfoReportes")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/InformeReportes.jsp");   
	        rd.forward(request, response);
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
