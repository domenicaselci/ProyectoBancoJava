<%@page import="Dominio.Cliente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mi Informacion</title>
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PagarPrestamo</title>
    <link  rel="stylesheet" type="text/css" href="Estilos.css"/>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>


</head>
<body>
	<%		Cliente cli = new Cliente();
		if(session.getAttribute("cliente")!= null){ 
		cli = (Cliente) session.getAttribute("cliente");%>
		
		<%	} 
		else
		{%>
		No se ha iniciado sesion
		<%} %>
	<div class="sidebar">
	    <h1 style="padding:10px;text-align:center;">HomeBanking Grupo 9</h1>
	    <hr>
	    <a href="InicioCliente.jsp">Inicio</a>
	    <a href="DatosPersonales.jsp">Datos Personales</a>
	    <a href="Prestamos.jsp">Prestamos</a>
	    <a href="servletTransferencias?Param=1">Transferencias</a>
	    <hr>
	    <br>
	    <img alt="" src="user.webp" style="width:20%;display: block;margin-left: auto;margin-right: auto;"> 
	    <h3 style="text-align:center;"><%=cli.getNombre()%>  <%=cli.getApellido()%></h3>
	    <a href="Login.jsp" style="text-align:center">Cerrar Sesion</a>
	</div>
	
	<div class="content">
		<form>
			<h1 style="text-align:center">Mi información</h1>


			<table style="text-align: left;border-spacing:15px;">
				<tr><td><b>DNI:</b></td><td><%=cli.getDni()%></td> </tr> 
				<tr><td><b>CUIL:</b></td><td><%=cli.getCuil()%></td> </tr> 
				<tr><td><b>Nombre:</b></td><td><%=cli.getNombre()%></td> </tr> 
				<tr><td><b>Apellido:</b></td><td><%=cli.getApellido()%></td> </tr>
				<tr><td><b>Sexo:</b></td><td><%=cli.getSexo()%></td></tr>
				<tr><td><b>Nacionalidad:</b></td><td><%=cli.getNacionalidad()%></td></tr>
				<tr><td><b>Fecha de Nacimiento:</b></td><td><%=cli.getFechaDeNacimiento()%></td></tr>
				<tr><td><b>Direccion:</b></td><td><%=cli.getDireccion()%></td></tr>
				<tr><td><b>Provincia:</b></td><td><%=cli.getProvincia().getNombre()%></td></tr>
				<tr><td><b>Localidad:</b></td><td><%=cli.getLocalidad().getNombre()%></td></tr>
				<tr><td><b>Email:</b></td><td><%=cli.getCorreoElectronico()%></td></tr>
				<tr><td><b>Telefono:</b></td><td><%=cli.getTelefono()%></td></tr>
				<tr><td><b>Usuario:</b></td><td><%=cli.getUsuarioCli().getUsuario()%></td></tr>						
			</table>
			
		
		</form>
	</div>


</body>
</html>