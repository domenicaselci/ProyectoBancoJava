<%@page import="Dominio.Prestamos"%>
<%@page import="Dominio.Usuarios"%>
<%@ page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Informes y Reportes</title>

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
    }

    table, th, td {
        border: 1px solid #333;
    }

    th, td {
        padding: 10px;
        text-align: center;
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
	
		<h1 style="text-align:center;">Informes y Reportes</h1>
		<form action="servletAdministrarMovimientos" method="post">
		
			<h3><u>Cantidad de dinero en concepto de préstamo</u></h3>
			
			<table>
				<tr> <td><p><b>Fecha:</b></p></td>  </tr>
				<tr><td>Fecha de Inicio</td> <td><Input type="Date" name="txtFechaIn"></td></tr>
				<tr><td>Fecha de Fin</td> <td><Input type="Date" name="txtFechaFin"></td></tr>
			</table>
			<div class="center-button" style="margin:10px">
			       <input type="submit" name="btnMostrarPrestamos" value="Mostrar Prestamos" style="width: 150px; height: 31px">
			    </div>
			<%
			   ArrayList<Prestamos> listaPrestamos=new ArrayList<Prestamos>();
			   if(request.getAttribute("prestamosEnBD")!=null){
				   listaPrestamos=(ArrayList<Prestamos>)request.getAttribute("prestamosEnBD");
			   }
			   ArrayList<Usuarios> listaUs=new ArrayList<Usuarios>();
			   if(request.getAttribute("usuariosEnBD")!=null){
				   listaUs=(ArrayList<Usuarios>)request.getAttribute("usuariosEnBD");
			   }
			   if(request.getAttribute("CompletarCamposInforme1")!=null){
					%><h4 class="mensajeError"> Debe completar ambos campos de fechas</h4><%
				}
	        if (listaPrestamos.size() != 0) {
	        %>
	        <table border="1" id="table_id">
		        	<thead>
		                <tr>
		                    <th>Prestamo</th>
		                    <th>DNI</th>
		                    <th>Fecha</th>
		                    <th>Importe Con Interes</th>
		                    <th>Importe Solicitado</th>
		                    <th>Plazo </th>
		                    <th>Cuotas </th>
		                    <th>Monto Cuota </th>
		                </tr>
		            </thead>
		            <tbody>
	            <%
	            float Monto = 0;
	            for (Prestamos a : listaPrestamos) {
	            		Monto += (float)a.getImporteConInteres();
	            		
	            %>
		            	<tr>
			                <td><%= a.getPrestamo()%></td>
			                <td><%= a.getDni().getDni() %></td>
			                <td><%= a.getFecha() %></td>
			                <td><%= a.getImporteSolicitado()%></td>
			                <td><%= a.getPlazo() %></td>
			                <td><%= a.getCuotas() %></td>
			                <td><%= a.getMontoCuotas() %></td>
			                <td><%= a.getImporteConInteres() %></td>
		            	</tr>
	            <%
	            }%></tbody>
	            	<thead>
				          <tr>
				             <th style="text-align:right">Monto Total: $<%= Monto %></th>
			           	  </tr>
	           		</thead>      
	        	<%}else if((request.getAttribute("prestamosEnBD")!=null)&&(request.getAttribute("CompletarCamposInforme1")==null)){
	        		%><table>
	                <tr>
	                    <td><h4 class="mensajeError">No se registraron prestamos</h4></td>
	                </tr>
	            </table>
	        	<%}
	            %>
	            
	        </table>
	<h3><u>Cantidad de usuarios dados de alta en fechas</u></h3>
	<table>
		<tr> <td><p><b>Fecha:</b></p></td>  </tr>
		<tr><td>Fecha de Inicio</td> <td><Input type="Date" name="txtUsuIn"></td></tr>
		<tr><td>Fecha de Fin</td> <td><Input type="Date" name="txtUsuFin"></td></tr>
	</table>
	<div class="center-button" style="margin:10px">
	   <input type="submit" name="btnMostrarUsuarios" value="Mostrar usuarios" style="width: 150px; height: 31px">
	</div>
		<% 
		if(request.getAttribute("CompletarCamposInforme2")!=null){
			%><h4 class="mensajeError"> Debe completar ambos campos de fechas</h4><%
		}
		if (listaUs.size() != 0) {
	        %>
				<table border="1" id="table_id">
		        	<thead>
		                <tr>
		                    <th>Usuario</th>
		                    <th>Fecha</th>
		                </tr>
		            </thead>
		            <tbody>
	            <%
	            int cant = 0;
	            for (Usuarios u : listaUs) {
	            		cant++;
	            		
	            %>
		            	<tr>
			                <td><%= u.getUsuario()%></td>
			                <td><%= u.getFechaCre() %></td>
		            	</tr>
	            <%
	            }%></tbody>
	            <thead>
		          <tr>
		             <th style="text-align:right">Cantidad: <%= cant %></th>
	           	  </tr>
	           		</thead>   
	        	<%}else if((request.getAttribute("usuariosEnBD")!=null)&&(request.getAttribute("CompletarCamposInforme2")==null)){
	        		%><table>
	                <tr>
	                    <td><h4 class="mensajeError">No se registraron usuarios</h4></td>
	                </tr>
	            </table>
	        	<%}
	            %>
	      </table>
	</form>
</div>

</body>
</html>