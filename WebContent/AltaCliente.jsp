<%@page import="Dominio.Provincia"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Dominio.Localidad"%>
<%@page import="Dominio.Cliente"%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alta Cliente</title>
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
        function confirmarAlta() {
            return confirm('¿Estás seguro de que deseas dar de alta este Cliente?');
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
		<h1 style="text-align: center;">Alta Clientes</h1>
		<form action="servletAdministrarClientes" method="post" onsubmit="return confirmarAlta();">
			<%
			ArrayList<Localidad> listaLocalidades = (ArrayList<Localidad>) request.getAttribute("listaLoc");
			ArrayList<Provincia> listaProvincias = (ArrayList<Provincia>) request.getAttribute("listaProv");
			Boolean dni = (Boolean)request.getAttribute("dni");
			Boolean cuil = (Boolean)request.getAttribute("cuil");
			%>
			<% 
			 if((request.getAttribute("contrasenaCoinciden")!=null)){
				 if((Boolean)request.getAttribute("contrasenaCoinciden")==false){
				 %> <h4 class="mensajeError"> Las contraseñas no coinciden</h4>     		
				 <% 
		 
				 }}
			 
			 if(request.getAttribute("agrego")!=null){
				 Boolean agregado=(Boolean)request.getAttribute("agrego");
				 if(agregado){
					 %> <h4 class="mensajeExitoso">Se agregó con exito! </h4>      		
				 <% 
				 }else{
				%> 
					 <h4 class="mensajeError"> No se pudo agregar</h4>
				<% 
				 }}
			%>
			<%if(dni!=null && dni){ %><h4 class="mensajeError">Formato de Dni Incorrecto. Intente nuevamente.</h4><%} %>
			<%if(cuil!=null && cuil){ %><h4 class="mensajeError">Formato de Cuil Incorrecto. Intente nuevamente.</h4></td></tr><%} %>
			<table id="miTabla">
				<tr><td>Nombre</td> <td><Input type="Text" pattern="[A-Za-z]+"  required name="txtNombre"></td></tr>
				<tr><td>Apellido</td> <td><Input type="Text" pattern="[A-Za-z]+"  required name="txtApellido"></td></tr>
				<tr><td>DNI</td> <td><Input type="Number" required name="txtDNI"> </td></tr>
				<tr><td>CUIL</td> <td><Input type="Number" required name="txtCUIL"></td></tr>
				<tr><td>Sexo</td> <td>	<Input type="Radio" required name="rbSexo" value="Femenino">Femenino<br>
										<Input type="Radio" name="rbSexo" value="Masculino">Masculino<br>
										<Input type="Radio" name="rbSexo" value="Otro">Otro</td></tr>									
				<tr><td>Nacionalidad</td> <td><Input type="String" required name="txtNacionalidad"></td></tr>
				<tr><td>Fecha de Nacimiento</td> <td><Input type="Date" required name="txtFechaNac"></td></tr>
				<tr><td>Dirección</td> <td><Input type="String" required name="txtDireccion"></td></tr>
				<tr><td>Provincia</td> <td>
					<Select name="Provincia" style="width: 126px;" required>
							<%if (listaProvincias != null){
								for (Provincia p: listaProvincias){ 
								%>
									<option value="<%=p.getIdProvincia()%>"><%=p.getNombre()%></option>
							<%	}}%>
						</Select>
					</td></tr>
				<tr><td>Localidad</td> <td>
						<Select name="Localidad" style="width: 126px;" required>
							<%if (listaLocalidades != null){
								for (Localidad l: listaLocalidades){ 
								%>
									<option value="<%=l.getIdLocalidad()%>"><%=l.getNombre()%></option>
							<%	}}%>
						</Select>
					</td></tr>			
				<tr><td>Correo Electronico</td> <td><Input type="email" required name="txtCorreo"></td></tr>
				<tr><td>Telefono</td> <td><Input type="tel" required name="txtTelefono"></td></tr>
				<tr><td>Usuario</td> <td><Input type="String" required name="txtUsuario"></td></tr>
				<tr><td>Contraseña</td> <td><Input type="password" required name="txtContrasena"></td></tr>
				<tr><td>Repetir Contraseña</td> <td><Input type="password" required name="txtContrasena2"></td></tr>
			</table>
			<div class="center-button" style="margin:10px">
        			<input type="submit" name="btnAceptar" value="Agregar" style="text-align:center;width: 40%;height:40px;">
    		</div>
			
		</form>
	</div>


</body>
</html>