<%@page import="Dominio.Cuenta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Eliminar Cuenta</title>
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
        function confirmarBaja() {
            return confirm('¿Estás seguro que desea eliminar esta Cuenta?');
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
<form action="servletAdministrarCuentas" method="post">
<div class="content">
	<h1 style="text-align: center;">Eliminar Cuenta</h1>
		
	
	<table>
            <tr>
                <td>Buscar por N° de Cuenta: </td>
                <td><input type="number"  name="txtNumeroDeCuenta"></td>
                <td><input type="submit" name="btnBuscarEliminar" value="Buscar" style="width: 121px; height: 31px"></td>
            </tr>
     </table>
     
		<%
		if(request.getAttribute("CompletarCampo")!=null){
			%><h4 class="mensajeError"> Debe completar el campo de busqueda</h4><%
		}
		if(request.getAttribute("cuentaNoExiste")!=null){
			%><h4 class="mensajeError"> El Nro de Cuenta ingresado no se encuentra registrado</h4><%
		}
		Cuenta nuevaCuenta = new Cuenta();
		if(request.getAttribute("Cuentaeli")!=null){
		 nuevaCuenta=(Cuenta)request.getAttribute("Cuentaeli");
				   %>
				   <table style="text-align: left;border-spacing:15px;">
							<tr><td><b>Nro Cuenta: </b></td> <td><%=nuevaCuenta.getCuenta()%><Input type="hidden" name="hCuenta" value=<%=nuevaCuenta.getCuenta()%>></td></tr>
							<tr><td><b>CBU: </b></td> <td><%=nuevaCuenta.getCbu()%></td></tr>
							<tr><td><b>Dni: </b></td> <td> <%=nuevaCuenta.getDni().getDni()%></td></tr>
							<tr><td><b>Fecha de creacion: </b></td> <td><%=nuevaCuenta.getFechadeCreacion()%></td></tr>
							<tr><td><b>Tipo de Cuenta: </b></td> <td><%=nuevaCuenta.getTipoDeCuenta().getIdTipo() %> </td></tr>
							<tr><td><b>Saldo: </b></td> <td><%=nuevaCuenta.getSaldo() %> </td></tr>
							<tr><td><b>Estado: </b></td> <td><%=nuevaCuenta.isEstado() %> </td></tr>
					</table>
					<div class="center-button" style="margin:10px">
        			<input type="submit" name="btnEliminar" value="Eliminar" onclick="return confirmarBaja();" style="text-align:center;width: 40%;height:40px;">
    			</div>
			 <%
			  }else{
				  
			  }
			 if(request.getAttribute("elimino")!=null){
				 Boolean eliminado=(Boolean)request.getAttribute("elimino");
				 if(eliminado){
				 
				 %> <h4 class="mensajeExitoso">Se elimino la cuenta con exito!</h4> <% 
				 }else{
					 %><h4 class="mensajeError">No se pudo eliminar la cuenta</h4><% 
				 }
			 }
			 
			%>


        
        
</div>
</form>

</body>
</html>