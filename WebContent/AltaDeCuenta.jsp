<%@page import="Dominio.TipoDeCuenta"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alta De Cuenta</title>
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
        function confirmarAlta() {
            return confirm('¿Estás seguro de que deseas dar de alta esta Cuenta?');
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
		
		<h1 style="text-align: center;">Dar de Alta Cuenta</h1>
		<form action="servletAdministrarCuentas" method="post" onsubmit="return confirmarAlta();">
			<%
			
			ArrayList<TipoDeCuenta> listaTipos=(ArrayList<TipoDeCuenta>) request.getAttribute("listaTDC"); 
			int nroCuenta=0;
			if(request.getAttribute("nroCuenta")!=null){nroCuenta=Integer.parseInt(request.getAttribute("nroCuenta").toString());}
			int cbu=nroCuenta+22000000;%>
			<% 
			 if(request.getAttribute("agrego")!=null){
				 Boolean agregado=(Boolean)request.getAttribute("agrego");
				 if(agregado){
					 %> <h4 class="mensajeExitoso"> Se agregó con exito! </h4>      		
				 <% 
				 }else{
				%> 
					 <h4 class="mensajeError"> No se pudo agregar</h4>
				<% 
				 }}
			if(request.getAttribute("limiteCuentas")!=null){
				%> 
				 <h4 class="mensajeError">Ha llegado al LÍMITE de 3 CUENTAS por Cliente</h4>
			<% 
			}
			Boolean dni = (Boolean)request.getAttribute("dni");
			if(dni!=null && dni){ %><h4 class="mensajeError">Formato de Dni Incorrecto. Intente nuevamente.</h4><%} 
			Boolean registroMov = (Boolean)request.getAttribute("registroMov");
			if(registroMov!=null && registroMov){ %><h4 class="mensajeExitoso">Se ha acreditado el dinero asignado al ALTA</h4><%}
			%>
		 <table>
		 
		 <tr>	<td> <p>Nro Cuenta</p></td>
		 		<td> <%=nroCuenta %> <input type="hidden" name="hNroCuenta" value=<%=nroCuenta %>></td></tr>
		 <tr>	<td> <p>DNI </p></td>
		 		<td> <input type="number" required name="txtDNI"></td></tr>
		 <tr>	<td> <p>Tipo de Cuenta: </p> </td>
		 		<td> <select name= "TipoDeCuenta">	
		 			<%
		 				if(listaTipos!=null){
		 					for(TipoDeCuenta t:listaTipos){
		 						%>
		 						<option value="<%= t.getIdTipo()%>"><%= t.getDescripcion() %></option>
		 						<%
		 					}
		 				}
		 			%>
		 			</select></td></tr>
		 <tr>	<td> <p>CBU:</p></td>
		 		<td> <%=cbu %><input type=hidden name="hCbu" value=<%=cbu %>></td></tr>
		 <tr>	<td> <p>Saldo: </p></td>
		 		<td> $ 10.000 (Monto inicial fijo)</td></tr>
		 </table>
		 <div class="center-button" style="margin:10px">
        			<input type="submit" name="btnDarAlta" value="Agregar" style="text-align:center;width: 40%;height:40px;">
    	</div>
    	
	</form>
</div>

</body>
</html>