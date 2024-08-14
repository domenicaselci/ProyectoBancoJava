package servlets;

import java.io.IOException;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DaoImple.Conexion;
import DaoImple.DaoClienteImple;
import Dominio.Cliente;
import Dominio.Cuenta;
import Dominio.Localidad;
import Dominio.Provincia;
import Dominio.Usuarios;
import Excepciones.CompararCuilException;
import Excepciones.CompararCuotasException;
import Excepciones.SaldoNegativoException;
import Excepciones.compararDniException;
import NegocioImple.NegocioClienteImple;
import NegocioImple.NegocioCuentaImple;
import NegocioImple.NegocioLocalidadImple;
import NegocioImple.NegocioProvinciaImple;
import NegocioImple.NegocioUsuarioImple;


/**
 * Servlet implementation class servletAdministrarClientes
 */
@WebServlet("/servletAdministrarClientes")
public class servletAdministrarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public servletAdministrarClientes() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("btnAltaCliente")!=null) {
			cargarLocalidades(request, response);
			cargarProvincias(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/AltaCliente.jsp");   
	        rd.forward(request, response);
		}
		if(request.getParameter("btnBajaCliente")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/BajaCliente.jsp");   
	        rd.forward(request, response);
		}
		if(request.getParameter("btnModificarCliente")!=null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ModificarCliente.jsp");   
	        rd.forward(request, response);
		}	
		if(request.getParameter("btnListarCliente")!=null) {
			cargarTodosClientesActivos(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/ListarClientes.jsp");   
	        rd.forward(request, response);
		}	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente clienteEliminar=new Cliente();
		Cliente clienteModificar=new Cliente();
		
		// ALTA CLIENTE
		if(request.getParameter("btnAceptar")!=null) {			
			String contrasena1=(String)request.getParameter("txtContrasena");
			String contrasena2=(String)request.getParameter("txtContrasena2");
			Boolean ContrasenaCoinciden=contrasena1.equals(contrasena2);
			if(ContrasenaCoinciden) {
			
			NegocioUsuarioImple NegUsuario = new NegocioUsuarioImple();
			NegocioClienteImple NegCliente=new NegocioClienteImple();
			
			Cliente cli=new Cliente();
			Usuarios us=new Usuarios();
			Provincia prov=new Provincia();
			Localidad loc=new Localidad();
			
			Boolean insertoUsuario=false;
			Boolean inserto=false;
			Boolean dni = false;
			Boolean cuil = false;
	        
			cli.setNombre(request.getParameter("txtNombre"));
			cli.setApellido(request.getParameter("txtApellido"));
			
			try {
			    cli.compararDni(request.getParameter("txtDNI"));
			} catch (compararDniException e) {
			    dni = true; 
			} 
			try {
			    cli.compararCuil(request.getParameter("txtCUIL"));
			} catch (CompararCuilException e) {
			    cuil = true; 
			} 
			
			if(!dni) {
				cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));
			}		
			
			if(!cuil) {
				cli.setCuil((request.getParameter("txtCUIL")));
			}
			
			cli.setSexo(request.getParameter("rbSexo"));
			cli.setNacionalidad(request.getParameter("txtNacionalidad"));
			
			DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" );
			LocalDate ld = LocalDate.parse( request.getParameter("txtFechaNac").toString(), f );
			LocalDate cal = LocalDate.parse(LocalDate.now().toString(),f);
			
			cli.setFechaDeNacimiento(ld);
			
			cli.setDireccion(request.getParameter("txtDireccion"));
			cli.setCorreoElectronico(request.getParameter("txtCorreo"));
			cli.setTelefono(Integer.parseInt(request.getParameter("txtTelefono")));
			
			prov.setIdProvincia(Integer.parseInt(request.getParameter("Provincia")));
			
			loc.setIdLocalidad(Integer.parseInt(request.getParameter("Localidad")));
			loc.setIdProvincia(prov);
			
			us.setUsuario(request.getParameter("txtUsuario"));
			us.setContrasena(request.getParameter("txtContrasena"));
			us.setFechaCre(cal);
		
			cli.setUsuarioCli(us);
			cli.setLocalidad(loc);
			cli.setIdProvincia(prov);
			cli.setEstado(true);
			
			if(!dni && !cuil) {
				insertoUsuario=NegUsuario.insert(us);
				if(insertoUsuario==true) {
			        inserto=NegCliente.insert(cli);
					}	
			}
			
	        request.setAttribute("agrego", inserto);
	        request.setAttribute("dni", dni);
	        request.setAttribute("cuil", cuil);
			
			}
			request.setAttribute("contrasenaCoinciden", ContrasenaCoinciden);
	        cargarLocalidades(request, response);
	        cargarProvincias(request, response);
	        RequestDispatcher rd = request.getRequestDispatcher("/AltaCliente.jsp");
	        rd.forward(request, response);
	        
			
		}
		
		//LISTAR CLIENTE
		
		if(request.getParameter("btnBuscarListar")!=null) {
			NegocioClienteImple negCli=new NegocioClienteImple();
			NegocioUsuarioImple negUsu=new NegocioUsuarioImple();
			Cliente cli=new Cliente();
			Cliente clienteBuscado=new Cliente();
			int entroCatch=0;
			
			try {
				cli.setDni(Integer.parseInt(request.getParameter("txtDni")));
			}catch (NumberFormatException e) {
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				entroCatch++;	
			}
			Boolean existe=negCli.existeCliente(cli);
			List<Usuarios> usuarios=negUsu.readAll();
			if(existe) {
				clienteBuscado=negCli.obtenerpordni(cli);
				if(clienteBuscado.isEstado()==true) {
				for(Usuarios u:usuarios) {
					if((clienteBuscado.getUsuarioCli().getUsuario()).equals(u.getUsuario())) {
						clienteBuscado.setUsuarioCli(u);
					}
				}
				
				request.setAttribute("clienteBuscado", clienteBuscado);				
				}
			}else if(entroCatch==0) {
				request.setAttribute("existe", existe);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/ListarClientes.jsp");
	        rd.forward(request, response);
		
		
		}
		if(request.getParameter("btnMostrar")!=null) {
			cargarTodosClientesActivos(request, response);
			
			RequestDispatcher rd = request.getRequestDispatcher("/ListarClientes.jsp");
	        rd.forward(request, response);

		}
		
		// ELIMINAR CLIENTE
		if(request.getParameter("btnBuscarEliminar")!=null) {
			NegocioClienteImple negCli= new NegocioClienteImple();
			Cliente cliente = new Cliente();
			int entro=0;
			
			try{
				cliente.setDni(Integer.parseInt(request.getParameter("txtDni")));
			}catch(NumberFormatException e) {
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				entro++;
			}
			
			if(negCli.existeCliente(cliente)==true) {
				clienteEliminar = negCli.obtenerpordni(cliente);
				request.setAttribute("clienteDNI",clienteEliminar);
			}else if(entro==0){
				request.setAttribute("clienteNoExiste"," No Existe");
			}			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BajaCliente.jsp");
	        rd.forward(request, response);
		}
		if(request.getParameter("btnEliminar")!=null) {
			NegocioClienteImple negCli= new NegocioClienteImple();
			Boolean elimino=false;
			clienteEliminar.setDni(Integer.parseInt(request.getParameter("hDni")));
			clienteEliminar=negCli.obtenerpordni(clienteEliminar);
			elimino=negCli.bajalogica(clienteEliminar);
			
			request.setAttribute("elimino",elimino);
			RequestDispatcher rd = request.getRequestDispatcher("/BajaCliente.jsp");
	        rd.forward(request, response);
		}	
		
		// MODIFICAR CLIENTE
		if(request.getParameter("btnBuscarModificar")!=null) {
			NegocioClienteImple negCli= new NegocioClienteImple();
			Cliente cliente = new Cliente();
			NegocioUsuarioImple negUsu=new NegocioUsuarioImple();
			List<Usuarios> usuarios=negUsu.readAll();
			
			try{
				cliente.setDni(Integer.parseInt(request.getParameter("txtDni")));
			}catch (NumberFormatException e) {
				request.setAttribute("CompletarCampo", "Debe Completar el Campo");
				
			}
			
			Boolean existe=negCli.existeCliente(cliente);
			if(existe) {
				clienteModificar = negCli.obtenerpordni(cliente);
				for(Usuarios us:usuarios) {
					if(clienteModificar.getUsuarioCli().getUsuario().equals(us.getUsuario())) {
						clienteModificar.setUsuarioCli(us);
					}
				}
				cargarLocalidades(request, response);
				cargarProvincias(request, response);
			}
			
			request.setAttribute("existe", existe);
			request.setAttribute("clienteDNI",clienteModificar);
			
			RequestDispatcher rd = request.getRequestDispatcher("/ModificarCliente.jsp");
	        rd.forward(request, response);
		}
		if(request.getParameter("btnGuardar")!=null) {
			String contrasena1=(String)request.getParameter("txtContraseña");
			String contrasena2=(String)request.getParameter("txtContraseña2");
			Boolean ContrasenaCoinciden=contrasena1.equals(contrasena2);
			if(ContrasenaCoinciden) {
			NegocioClienteImple NegCliente=new NegocioClienteImple();
			Cliente cli=new Cliente();
			Usuarios us=new Usuarios();
			Provincia prov=new Provincia();
			Localidad loc=new Localidad();
			
			Boolean modifico=false;
			Boolean cuil=false;
			
			try {
			    cli.compararCuil(request.getParameter("txtCUIL"));
			} catch (CompararCuilException e) {
			    cuil = true; 
			} 
		
			if(!cuil) {
				cli.setCuil((request.getParameter("txtCUIL")));
			}
			
			String Nombre=request.getParameter("txtNombre");
			String Apellido=request.getParameter("txtApellido");
			String Nacionalidad=request.getParameter("txtNacionalidad");
			String Direccion=request.getParameter("txtDireccion");
			String Correo=request.getParameter("txtCorreo");
			
			if((!Nombre.isEmpty())&&(!Apellido.isEmpty())&&(!Nacionalidad.isEmpty())&&(!Direccion.isEmpty())&&(!Correo.isEmpty())&&(!contrasena1.isEmpty())) {
				cli.setNombre(Nombre);
				cli.setApellido(Apellido);
				cli.setNacionalidad(Nacionalidad);
				cli.setDireccion(Direccion);
				cli.setCorreoElectronico(Correo);
				
				int cVacioCampo=0;
				try {
					cli.setDni(Integer.parseInt(request.getParameter("hDni")));			
					cli.setSexo(request.getParameter("rbSexo"));
					cli.setTelefono(Integer.parseInt(request.getParameter("txtTelefono")));
					cli.setEstado(Boolean.parseBoolean(request.getParameter("rbEstado")));
					
					DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
					LocalDate ld = LocalDate.parse(request.getParameter("txtFechaNac").toString(), f );
					LocalDate cal = LocalDate.parse(LocalDate.now().toString(),f);
							
					prov.setIdProvincia(Integer.parseInt(request.getParameter("Provincia")));
					
					loc.setIdLocalidad(Integer.parseInt(request.getParameter("Localidad")));
					loc.setIdProvincia(prov);
					
					us.setUsuario(request.getParameter("hUsuario"));
					us.setContrasena(contrasena1);
					us.setFechaCre(cal);
					
					cli.setFechaDeNacimiento(ld);
					cli.setUsuarioCli(us);
					cli.setLocalidad(loc);
					cli.setIdProvincia(prov);
				
				} catch (NumberFormatException e) {
					request.setAttribute("CompletarCamposAModificar", true);
					cVacioCampo++;
				}
				if((cuil==false)&&(cVacioCampo==0)) {
			        modifico=NegCliente.modificar(cli);
			        
			        }
			}else {
				request.setAttribute("CompletarCamposAModificar", true);
			}
			
	
	        request.setAttribute("cuil", cuil);
	        request.setAttribute("modifico", modifico);
	        }
			request.setAttribute("contrasenaCoinciden", ContrasenaCoinciden);
	        cargarLocalidades(request, response);
	        cargarProvincias(request, response);
	        RequestDispatcher rd = request.getRequestDispatcher("/ModificarCliente.jsp");
	        rd.forward(request, response);
			
			
		}
		
		if(request.getParameter("BuscarLike")!=null) {
			
			String Texto;
			ArrayList<Cliente> lcList=new ArrayList<Cliente>();
			
			 Texto= request.getParameter("Apellido");
			 	NegocioClienteImple negocioClienteImple=new NegocioClienteImple();
				lcList=(ArrayList<Cliente>)negocioClienteImple.obtenerClienteLike(Texto);
				request.setAttribute("ListaCli", lcList);

		
				
			RequestDispatcher rd = request.getRequestDispatcher("/ListarClientes.jsp");
			rd.forward(request, response);
		}
		
		
		
		
		
		
		
	}
	
			
	protected void cargarLocalidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    NegocioLocalidadImple negLoc = new NegocioLocalidadImple();
	    ArrayList<Localidad> localidades = (ArrayList<Localidad>) negLoc.readAll();
	    request.setAttribute("listaLoc", localidades);
	    
   
	}
	protected void cargarProvincias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    NegocioProvinciaImple negProv = new NegocioProvinciaImple();
	    ArrayList<Provincia> provincias = (ArrayList<Provincia>) negProv.readAll();	    
	    request.setAttribute("listaProv", provincias);
   
	}
	private void cargarTodosClientesActivos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NegocioClienteImple negCli= new NegocioClienteImple();
		NegocioUsuarioImple negUsu=new NegocioUsuarioImple();
		
		List<Cliente> clientes=negCli.readAll();
		List<Usuarios> usuarios=negUsu.readAll();
		List<Cliente> clientesvalidos = new ArrayList<Cliente>(); 
		
		for(Cliente cli:clientes) {
			if(cli.isEstado()==true) {
			for(Usuarios us:usuarios) {
				if(cli.getUsuarioCli().getUsuario().equals(us.getUsuario())) {
					cli.setUsuarioCli(us);
					clientesvalidos.add(cli);
					}
				}
			}
		}
		
		request.setAttribute("clienteEnBD",clientesvalidos);

	}
	
	/*protected Cliente obtenerClientelogueado(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		NegocioClienteImple negCli = new NegocioClienteImple();
		Cliente cli = new Cliente();
		HttpSession session = request.getSession();
        session.setAttribute("usuario", usuNuevo);
	}*/
}
