<%@page import="Dominio.Cliente"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Baja Cliente</title>
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
    	text-align:center;}
</style>

    <script>
        function confirmarBaja() {
            return confirm('¿Estás seguro que desea eliminar este Cliente?');
        }
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
		<form action="servletAdministrarClientes" method="post"">
			<h1 style="text-align: center;">Baja Cliente</h1>
			<table>
            <tr>
                <td>Buscar por DNI: </td>
                <td><input type="number" name="txtDni"></td>
                <td><input type="submit" name="btnBuscarEliminar" value="Buscar" style="width: 121px; height: 31px"></td>
            </tr>
        	</table>
			<% 
			if(request.getAttribute("CompletarCampo")!=null){
				%><h4 class="mensajeError"> Debe completar el campo de busqueda</h4><%
			}
			if(request.getAttribute("clienteNoExiste")!=null){
				%><h4 class="mensajeError"> El DNI no se encuentra registrado</h4><%
			}
			Cliente nuevoCli = new Cliente();
			 if(request.getAttribute("clienteDNI")!=null){
				   nuevoCli=(Cliente)request.getAttribute("clienteDNI");
				   %>
				    <table style="text-align: left;border-spacing:15px;">
					<tr><td><b>DNI:</b></td><td><%=nuevoCli.getDni()%> <input type="hidden" name="hDni" value=<%=nuevoCli.getDni()%>></td> </tr> 
					<tr><td><b>CUIL:</b></td><td><%=nuevoCli.getCuil()%></td> </tr> 
					<tr><td><b>Nombre:</b></td><td><%=nuevoCli.getNombre()%></td> </tr> 
					<tr><td><b>Apellido:</b></td><td><%=nuevoCli.getApellido()%></td> </tr>
					<tr><td><b>Sexo:</b></td><td><%=nuevoCli.getSexo()%></td></tr>
					<tr><td><b>Nacionalidad:</b></td><td><%=nuevoCli.getNacionalidad()%></td></tr>
					<tr><td><b>Fecha de Nacimiento:</b></td><td><%=nuevoCli.getFechaDeNacimiento()%></td></tr>
					<tr><td><b>Direccion:</b></td><td><%=nuevoCli.getDireccion()%></td></tr>
					<tr><td><b>Provincia:</b></td><td><%=nuevoCli.getProvincia().getNombre()%></td></tr>
					<tr><td><b>Localidad:</b></td><td><%=nuevoCli.getLocalidad().getNombre() %></td></tr>
					<tr><td><b>Email:</b></td><td><%=nuevoCli.getCorreoElectronico()%></td></tr>
					<tr><td><b>Telefono:</b></td><td><%=nuevoCli.getTelefono()%></td></tr>
					<tr><td><b>Usuario:</b></td><td><%=nuevoCli.getUsuarioCli().getUsuario()%></td></tr>
				</table>
				<div class="center-button" style="margin:10px">
        			<input type="submit" name="btnEliminar" value="Eliminar" onclick="return confirmarBaja();" style="text-align:center;width: 40%;height:40px;">
    			</div>
			 <%
			  }
			 if(request.getAttribute("elimino")!=null){
				 Boolean eliminado=(Boolean)request.getAttribute("elimino");
				 if(eliminado){
				 
				 %><h4 class="mensajeExitoso">Se elimino con exito!</h4><% 
				 }else{
					 %><h4 class="mensajeError">No se pudo eliminar</h4><% 
				 }
			 }
			 
			%>
		</form>
	</div>

</body>
</html>