
<%@page import="Dominio.Cliente"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Clientes</title>
<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
    }

    .sidebar {
        height: 100%;
        width: 250px;
        position: fixed;
        background-color: #333;
        padding-top: 20px;
        color: white;
    }

    .sidebar a {
        padding: 10px 15px;
        text-decoration: none;
        font-size: 18px;
        color: white;
        display: block;
    }

    .sidebar a:hover {
        background-color: #555;
    }

    .content {
        margin-left: 250px;
        padding: 20px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 10px;
    }

    table, th, td {
        border: 1px solid #333;
    }

    th, td {
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #333;
        color: white;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .search-form {
        margin-bottom: 20px;
    }

    .center-button {
        text-align: center;
        margin:10px;
    }
    .mensajeError{
    	color:red;
    	text-align:center;
    }
    .mensajeExitoso{
    	text-align:center;
    }
</style>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable({
	        "language": {
	            "url": "//cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
	        },
	
		});
		
	});
	
</script>
</head>
<body>
	<div class="sidebar">
	    <h1 style="padding:10px;text-align:center;">HomeBanking Grupo 9</h1>
	    <hr>
	    <a href="MenuAdmin.jsp">Inicio</a>
	    <a href="AdministrarCliente.jsp">Administrar Clientes</a>
	    <a href="AdministrarCuentas.jsp">Administrar Cuentas</a>
	    <a href="servletAutorizarPrestamos?Param=1">Autorizar Prestamos</a>
	    <a href="InformeReportes.jsp">Informes y Reportes</a>
	    <hr>
	    <a href="Login.jsp">Cerrar Sesion</a>
	</div>


	<div class="content">
		<form action="servletAdministrarClientes" method="post">
		<h1 style="text-align: center;">Listar Clientes</h1>
		<table>
            <tr>
                <td>Ingrese el DNI: </td><td><Input type="number" name="txtDni"></td>
                <td><Input type="submit" name="btnBuscarListar" value="Buscar"></td>
                <td><Input type="submit" name="btnMostrar" value="MostrarTodos"></td>
                <td>Ingrese el Apellido: </td><td><Input type="text" name="Apellido" ></td>
                <td><Input type="submit" name="BuscarLike" value="Buscar Apellido"></td>
            </tr>
        </table>
		<%
		if(request.getAttribute("CompletarCampo")!=null){
			%><h4 class="mensajeError""> Debe completar el campo de busqueda</h4><%
		}else{
		
			ArrayList<Cliente> clientes=new ArrayList<Cliente>();
			
			if (request.getAttribute("ListaCli")!=null){
				clientes=(ArrayList<Cliente>)request.getAttribute("ListaCli");
			}
		  if(request.getAttribute("clienteEnBD")!=null){
			   clientes=(ArrayList<Cliente>)request.getAttribute("clienteEnBD");
			   
		   }
		   Cliente cli=new Cliente();
		   if(request.getAttribute("clienteBuscado")!=null){
			   cli=(Cliente)request.getAttribute("clienteBuscado");
		   }
		   
		   if (clientes.size() == 0 && (cli.getDni() == 0)) {
		 %>
		        <table>
		            <tr>
		                <td><h4 class="mensajeError">No se registraron clientes</h4></td>
		            </tr>
		        </table>
		  <%
		   }
		   if(clientes.size() != 0){
			 %>
			 <table border="1" id="table_id">
            		<thead>
                	<tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>DNI</th>
                    <th>CUIL</th>
                    <th>Sexo</th>
                    <th>Nacionalidad</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Dirección</th>
                    <th>Localidad</th>
                    <th>Provincia</th>
                    <th>Correo Electronico</th>
                    <th>Telefono</th>
                    <th>Usuario</th>
                    <!--  <th>Contraseña</th>-->
                	</tr>
            		</thead>
            		<tbody>
			 <%
		      for(Cliente a: clientes){
		    	 %>
		    	  <tr>
			    	 <td><%= a.getNombre() %></td>
			         <td><%= a.getApellido()%></td>
			         <td><%= a.getDni()%></td>
			         <td><%= a.getCuil() %></td>
			         <td><%= a.getSexo()%></td>
			         <td><%= a.getNacionalidad()%></td>
			         <td><%= a.getFechaDeNacimiento()%></td>
			         <td><%= a.getDireccion() %></td>
			         <td><%= a.getLocalidad().getNombre()%></td>
			         <td><%= a.getProvincia().getNombre()%></td>
			         <td><%= a.getCorreoElectronico()%></td>
			         <td><%= a.getTelefono()%></td>
			         <td><%= a.getUsuarioCli().getUsuario()%></td>
			        <!--  <td><%= a.getUsuarioCli().getContrasena()%></td>      -->
		          <%
            }
            %></tbody>
        </table>
        <%
        	}
		   if(cli.getDni()!=0){
		   %>
		      <table border="1" id="table_id">
		        	<thead>
                	<tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>DNI</th>
                    <th>CUIL</th>
                    <th>Sexo</th>
                    <th>Nacionalidad</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Dirección</th>
                    <th>Localidad</th>
                    <th>Provincia</th>
                    <th>Correo Electronico</th>
                    <th>Telefono</th>
                    <th>Usuario</th>
                   <!--  <th>Contraseña</th> -->
                	</tr>
            		</thead>
            		<tbody>
		         	  <tr>
		     	    	 <td><%= cli.getNombre() %></td>
		     	         <td><%= cli.getApellido()%></td>
		     	         <td><%= cli.getDni()%></td>
		     	         <td><%= cli.getCuil()%></td>
		     	         <td><%= cli.getSexo()%></td>
		     	         <td><%= cli.getNacionalidad()%></td>
		     	         <td><%= cli.getFechaDeNacimiento()%></td>
		     	         <td><%= cli.getDireccion() %></td>
		     	         <td><%= cli.getLocalidad().getNombre()%></td>
		     	         <td><%= cli.getProvincia().getNombre()%></td>
				         <td><%= cli.getCorreoElectronico()%></td>
				         <td><%= cli.getTelefono()%></td>
				         <td><%= cli.getUsuarioCli().getUsuario()%></td>
				        <!--  <td><input type="password" value=<%= cli.getUsuarioCli().getContrasena()%> ></td> -->   
		               </tr>
		               </tbody>
		               </table>
		             <%
		        	   
		           } }
	            
	            %>
	    
  
 </form>
</div>
</body>
</html>