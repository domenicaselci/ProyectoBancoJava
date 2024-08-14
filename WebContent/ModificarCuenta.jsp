<%@ page import="java.util.ArrayList"%>
<%@ page import="Dominio.TipoDeCuenta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>
    <%@page import="Dominio.Cuenta"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificar Cuenta</title>
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

    <script>
        function confirmarModificacion() {
            return confirm('¿Estás seguro de que deseas modificar esta Cuenta?');
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
	<h1 style="width: 811px; text-align: center;">Modificar Cuenta</h1>
		
<form action="servletAdministrarCuentas" method="post">
			<table>
            <tr>
                <td>Buscar por N° de Cuenta:</td>
                <td><input type="text" name="txtNumeroDeCuenta"></td>
                <td><input type="submit" name="btnBuscarModificar" value="Buscar" style="width: 121px; height: 31px"></td>
            </tr>
        	</table>
	
			 		<%
			 		
			 		Boolean dni=(Boolean)request.getAttribute("dni");
					if(dni!=null && dni){ 
					%>	<h4 class="mensajeError">Formato de DNI Incorrecto. Intente nuevamente.</h4>
					<%} 
					
					if(request.getAttribute("CompletarCamposAModificar")!=null){ 
						%>	<h4 class="mensajeError">Debe completar todos los campos para modificar.</h4>
						<%}
			 		Cuenta nuevaCuenta=new Cuenta();
			 		if(request.getAttribute("existe")!=null){
			 			if((boolean)request.getAttribute("existe")==true){
			 				ArrayList<TipoDeCuenta> listaTipos=(ArrayList<TipoDeCuenta>)request.getAttribute("listaTDC");
			 				if(request.getAttribute("cuentaModificar")!=null){
				   			nuevaCuenta=(Cuenta)request.getAttribute("cuentaModificar");
			 		
			 		
				   		%>
				   		<table>
							<tr><td>Nro Cuenta:</td> <td><%=nuevaCuenta.getCuenta()%><Input type="hidden" name="hCuenta" value=<%=nuevaCuenta.getCuenta()%>></td></tr>
							<tr><td>CBU: </td> <td><Input type="number" name="txtCbu" value=<%=nuevaCuenta.getCbu()%>></td></tr>
							<tr><td>Dni:</td> <td><Input type="number" name="txtDni" value=<%=nuevaCuenta.getDni().getDni()%>></td></tr>
							<tr><td>Fecha de creacion</td> <td><Input type="Date" name="txtFechaCreacion" value=<%=nuevaCuenta.getFechadeCreacion()%>></td></tr>
							<tr><td>Tipo de Cuenta</td> 
								<td><Select name="TiposCuenta">
									<%
						 				if(listaTipos!=null){
						 					for(TipoDeCuenta t:listaTipos){
						 						%>
						 						<option value="<%= t.getIdTipo()%>" <% if (t.getIdTipo()==(nuevaCuenta.getTipoDeCuenta().getIdTipo())) { %> selected <% } %>><%= t.getDescripcion() %></option>
						 						<%
						 					}
						 				}
						 			%>
								</Select>	
								</td></tr>
							<tr><td>Saldo</td> <td><Input type="Text" name="txtSaldo" pattern=[0-9]+([,\.][0-9]+)? value=<%=nuevaCuenta.getSaldo() %>></td></tr>
							<tr><td>Estado</td> <td><Input type="Radio" required name="rbEstado" value="true" <% if (nuevaCuenta.isEstado()==true) { %> checked <% } %>>Activa<br>
										<Input type="Radio" name="rbEstado" value="false" <% if (nuevaCuenta.isEstado()==false) { %> checked <% } %>>Inactiva<br></td></tr>
							
					</table>
					<div class="center-button" style="margin:10px">
        				<input type="submit" name="btnModificar" value="Guardar Cambios" onclick="return confirmarModificacion();" style="text-align:center;width: 40%;height:40px;">
    				</div>
							<%
							
			 		}
			 		}
			 		else if(request.getAttribute("CompletarCampo")!=null){
						%><h4 class="mensajeError"> Debe completar el campo de busqueda</h4><%
					}else{
			 		%>
			 		<h4 class="mensajeError">No existe registro</h4>
			 		<% 
			 		}}
			 		if(request.getAttribute("modifico")!=null){
					if((boolean)request.getAttribute("modifico")==true){
						%><h4 class="mensajeExitoso">Se ha modificado con exito!</h4><%
					}else{
						%><h4 class="mensajeError">No se ha podido modificar</h4><%
					}
				}
			 		%> 
        
        </form>
</div>
</body>
</html>