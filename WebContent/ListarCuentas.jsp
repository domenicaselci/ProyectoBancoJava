<%@page import="Dominio.Cuenta"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Cuentas</title>
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
    <form action="servletAdministrarCuentas" method="post" class="search-form">
        <h1 style="text-align: center;">Listar Cuentas</h1>
        <%
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        if (request.getAttribute("cuentaEnBD") != null) {
            cuentas = (ArrayList<Cuenta>) request.getAttribute("cuentaEnBD");
        }
        Cuenta buscado = new Cuenta();
        if (request.getAttribute("cuentaObtenida") != null) {
            buscado = (Cuenta) request.getAttribute("cuentaObtenida");
        }
        if(request.getAttribute("ListacuentaFiltro")!= null){
        	
        	cuentas=(ArrayList<Cuenta>) request.getAttribute("ListacuentaFiltro");
        }
	 	if(request.getAttribute("ListaFiltroFechas")!= null){
	        	
	        	cuentas=(ArrayList<Cuenta>) request.getAttribute("ListaFiltroFechas");
	        }
	 	if(request.getAttribute("CompletarCampo")!=null){
			%><h4 class="mensajeError"> Debe completar el campo de busqueda</h4><%
		}
	 	if(request.getAttribute("CompletarCampoFiltro")!=null){
			%><h4 class="mensajeError"> Debe completar ambos campos de filtro</h4><%
		}%>
        <table>
            <tr>
                <td>Buscar por DNI:</td>
                <td><input type="number" name="txtBuscarDni"></td>
                <td><input type="submit" name="BTNBuscarDni" value="BUSCAR" style="width: 121px; height: 31px"></td>
            </tr>
            <tr>
                <td>Buscar por Nro Cuenta:</td>
                <td><input type="number" name="txtBuscarCuenta"></td>
                <td><input type="submit" name="BTNBuscarCuenta" value="BUSCAR" style="width: 121px; height: 31px"></td>
            </tr>
            
            <tr>
        	<td>Filtro Saldos</td>
            <td><p>Desde: </p><input type="text" name="txtmontoIni" pattern=[0-9]+([,\.][0-9]+)?></td>
             <td><p>Hasta: </p><input type="text" name="txtMontoFin" pattern=[0-9]+([,\.][0-9]+)?></td>
             <td><input type="submit" name="BTNFiltrar" value="BUSCAR" style="width: 121px; height: 31px"></td>
            
            </tr>
            
            <tr>
        	<td>Filtro Fechas</td>
            <td><p>Desde: </p><input type="Date" name="txtFechaIni"></td>
             <td><p>Hasta: </p><input type="Date" name="txtFechaFin"></td>
             <td><input type="submit" name="BTNFiltrarFecha" value="BUSCAR" style="width: 121px; height: 31px"></td>
            
            </tr>
            
        </table>
    <div class="center-button" style="margin:10px">
        <input type="submit"  name="btnMostrar" value="Mostrar Cuentas" style="width: 121px; height: 31px">
    </div>
        <%
        if (cuentas.size() == 0 && (buscado.getCuenta() == 0)) {
        %>
        <table>
            <tr>
                <td><h4 class="mensajeError">No se registraron cuentas</h4></td>
            </tr>
        </table>
        <%
        }
        if (cuentas.size() != 0) {
        %>
        <table border="1" id="table_id">
            <thead>
                <tr>
                    <th>Cuenta</th>
                    <th>CBU</th>
                    <th>DNI</th>
                    <th>Fecha De Creacion</th>
                    <th>Tipo de Cuenta</th>
                    <th>Saldo</th>
                </tr>
            </thead>
            <tbody>
            <%
            for (Cuenta a : cuentas) {
            %>
            <tr>
                <td><%= a.getCuenta() %></td>
                <td><%= a.getCbu() %></td>
                <td><%= a.getDni().getDni() %></td>
                <td><%= a.getFechadeCreacion() %></td>
                <td><%= a.getTipoDeCuenta().getDescripcion() %></td>
                <td><%= a.getSaldo() %></td>
            </tr>
            
            <%
            }
            %>
            </tbody>
        </table>
        <%
        }
        if ((buscado.getCuenta() != 0) && (buscado.isEstado() == true)) {
        %>
        <table border="1" id="table_id">
        	<thead>
                <tr>
                    <th>Cuenta</th>
                    <th>CBU</th>
                    <th>DNI</th>
                    <th>Fecha De Creacion</th>
                    <th>Tipo de Cuenta</th>
                    <th>Saldo</th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td><%= buscado.getCuenta() %></td>
                <td><%= buscado.getCbu() %></td>
                <td><%= buscado.getDni().getDni() %></td>
                <td><%= buscado.getFechadeCreacion() %></td>
                <td><%= buscado.getTipoDeCuenta().getDescripcion() %></td>
                <td><%= buscado.getSaldo() %></td>
            </tr>
            </tbody>
        </table>
        <%
        }
        %>
    </form>
    </div>
</body>
</html>

