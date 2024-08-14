<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Provincia"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Dominio.Localidad"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificar Cliente</title>
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
        function confirmarModificacion() {
            return confirm('¿Estás seguro de que deseas modificar este Cliente?');
        }
    </script>
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
			<h1 style="text-align: center;">Modificar Cliente</h1>
			<table>
            <tr>
                <td>Buscar por DNI: </td>
                <td><input type="number" name="txtDni"></td>
                <td><input type="submit" name="btnBuscarModificar" value="Buscar" style="width: 121px; height: 31px"></td>
            </tr>
        	</table>
			<% 	
			if(request.getAttribute("modifico")!=null){
				if((boolean)request.getAttribute("modifico")==true){
					%> <h4 class="mensajeExitoso">Se ha modificado con exito!<h4> <%
				}else{
					%><h4 class="mensajeError">No se ha podido modificar<h4><%
					if(request.getAttribute("CompletarCamposAModificar")!=null){ 
					%>	<h4 class="mensajeError">Debe completar todos los campos para modificar.</h4>
					<%}
				}
			}
			Boolean cuil=(Boolean)request.getAttribute("cuil");
			if(cuil!=null && cuil){ 
			%>	<h4 class="mensajeError">Formato de Cuil Incorrecto. Intente nuevamente.</h4>
			<%}
			
			
			if((request.getAttribute("contrasenaCoinciden")!=null)){
				 if((Boolean)request.getAttribute("contrasenaCoinciden")==false){
				 %> <h4 class="mensajeError"> Las contraseñas no coinciden</h4>     		
				 <% 
		 
				 }}
			Cliente nuevoCli = new Cliente();
			if(request.getAttribute("existe")!=null){
				
				if((boolean)request.getAttribute("existe")==true){
					
					ArrayList<Localidad> listaLocalidades = (ArrayList<Localidad>) request.getAttribute("listaLoc");
					ArrayList<Provincia> listaProvincias = (ArrayList<Provincia>) request.getAttribute("listaProv");
					
			 		if(request.getAttribute("clienteDNI")!=null){
				   		nuevoCli=(Cliente)request.getAttribute("clienteDNI");
						   %>
						<table>
							<tr><td>Nombre</td> <td><Input type="String" name="txtNombre" pattern="[A-Za-z]+"  value=<%=nuevoCli.getNombre()%> ></td></tr>
							<tr><td>Apellido</td> <td><Input type="String" name="txtApellido" pattern="[A-Za-z]+" value=<%=nuevoCli.getApellido()%>></td></tr>
							<tr><td>DNI: </td> <td><%=nuevoCli.getDni()%><Input type="Hidden" name="hDni" value=<%=nuevoCli.getDni()%> ></td></tr>
							<tr><td>CUIL</td> <td><Input type="number" name="txtCUIL" value=<%= nuevoCli.getCuil() %>></td></tr>
							<tr><td>Sexo</td> <td>	<Input type="Radio" required name="rbSexo" value="Femenino" <% if ((nuevoCli.getSexo()).trim().equals("Femenino")) { %> checked <% } %>>Femenino<br>
										<Input type="Radio" name="rbSexo" value="Masculino" <% if ((nuevoCli.getSexo()).trim().equals("Masculino")) { %> checked <% } %>>Masculino<br>
										<Input type="Radio" name="rbSexo" value="Otro" <% if ((nuevoCli.getSexo()).trim().equals("Otro")) { %> checked <% } %>>Otro</td></tr>
							<tr><td>Nacionalidad</td> <td><Input type="String" name="txtNacionalidad" value=<%=nuevoCli.getNacionalidad()%>></td></tr>
							<tr><td>Fecha de Nacimiento</td> <td><Input type="Date" name="txtFechaNac" value=<%=nuevoCli.getFechaDeNacimiento()%>></td></tr>
							<tr><td>Dirección</td> <td><Input type="String" name="txtDireccion" value=<%=nuevoCli.getDireccion()%>></td></tr>
							<tr><td>Localidad</td> <td>
									<Select name="Localidad" style="width: 126px;">
										<%if (listaLocalidades != null){
											for (Localidad l: listaLocalidades){ 
											%>
												<option value="<%=l.getIdLocalidad()%>" <% if (l.getIdLocalidad()==(nuevoCli.getLocalidad().getIdLocalidad())) { %> selected <% } %>><%=l.getNombre()%></option>
										<%	}}%>
									</Select>
								</td></tr>
							<tr><td>Provincia</td> <td>
									<Select name="Provincia" style="width: 126px;">
											<%if (listaProvincias != null){
												for (Provincia p: listaProvincias){ 
												%>
													<option value="<%=p.getIdProvincia()%>" <% if (p.getIdProvincia()==(nuevoCli.getProvincia().getIdProvincia())) { %> selected <% } %>><%=p.getNombre()%></option>
											<%	}}%>
										</Select>
									</td></tr>
							<tr><td>Correo Electronico</td> <td><Input type="email" name="txtCorreo" value=<%=nuevoCli.getCorreoElectronico()%>></td></tr>
							<tr><td>Telefono</td> <td><Input type="number" name="txtTelefono" value=<%= nuevoCli.getTelefono() %>></td></tr>
							<tr><td>Usuario</td> <td><%=nuevoCli.getUsuarioCli().getUsuario()%><Input type="Hidden" name="hUsuario" value=<%=nuevoCli.getUsuarioCli().getUsuario()%>></td></tr>
							<tr><td>Contraseña</td> <td><Input type="password" name="txtContraseña" value=<%=nuevoCli.getUsuarioCli().getContrasena()%>></td></tr>
							<tr><td>Ingrese Contraseña Nuevamente</td> <td><Input type="password" name="txtContraseña2" value=<%=nuevoCli.getUsuarioCli().getContrasena()%>></td></tr>
							<tr><td>Estado</td> <td><Input type="Radio" required name="rbEstado" value="true" <% if ((nuevoCli.isEstado()==true)) { %> checked <% } %>>Activo<br>
										<Input type="Radio" name="rbEstado" value="false" <% if ((nuevoCli.isEstado()==false)) { %> checked <% } %>>Inactivo<br></td></tr>
							
						</table>
				<div class="center-button" style="margin:10px">
        			<input type="submit" name="btnGuardar" value="Guardar Cambios" onclick="return confirmarModificacion();" style="text-align:center;width: 40%;height:40px;">
    			</div>
						
					<%
			 	}
			 	}else if(request.getAttribute("CompletarCampo")!=null){
			 		%><h4 class="mensajeError">Debe completar el campo de busqueda</h4> <%
			 	}else
				{
			 		%><h4 class="mensajeError"> No existe el cliente</h4> <%
			 	}}
				
			  %>
			
			
		
		</form>
	</div>
</body>
</html>