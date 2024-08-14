<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="Dominio.Prestamos" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>HomeBanking Grupo 9</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color:#f4f4f4;
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
    <link  rel="stylesheet" type="text/css" href="Estilos.css"/>

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

        <script>
        function confirmarCambio() {
            return confirm('¿Estás seguro de que deseas actualizar el Estado?');
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
        <h1 style="text-align:center">Autorizar Prestamos</h1>

        <form action="servletAutorizarPrestamos"  method="post" onsubmit="return confirmarCambio();">
            <table>
                <tr>
                    <td><h3>Prestamos a autorizar: </h3></td>
                    <td><input type="number" required name="idPrestamo" style="width: 253px; "></td>
                    <td><input type="submit" value="Autorizar" name="AutorizarBtn" style="width: 121px; height: 31px"></td>
                    <td><input type="submit" value="Rechazar" name="RechazarBtn" style="width: 121px; height: 31px"></td>
                </tr>
            </table>
			
            <h1>Listado de Préstamos</h1>
           <table id="table_id">
                <thead>
                    <tr>
                        <th>Prestamo</th>
                        <th>DNI</th>
                        <th>Fecha</th>
                        <th>Importe con Interés</th>
                        <th>Importe Solicitado</th>
                        <th>Plazo</th>
                        <th>Cuotas</th>
                        <th>Monto Cuotas</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Prestamos> listaPrestamos = (List<Prestamos>)request.getAttribute("listaPrestamos"); %>
                    <% if (listaPrestamos != null) {
                        for (Prestamos prestamo : listaPrestamos) { %>
                            <tr>
                                <td><%= prestamo.getPrestamo() %></td>
                                <td><%= prestamo.getDni().getDni() %></td>
                                <td><%= prestamo.getFecha() %></td>
                                <td><%= prestamo.getImporteConInteres() %></td>
                                <td><%= prestamo.getImporteSolicitado() %></td>
                                <td><%= prestamo.getPlazo() %></td>
                                <td><%= prestamo.getCuotas() %></td>
                                <td><%= prestamo.getMontoCuotas() %></td>
                                <td>
                                    <% if (prestamo.getEstado() == 1) { %>
                                        Pendiente
                                    <% } else if (prestamo.getEstado() == 2) { %>
                                        Aprobado
                                    <% } else if (prestamo.getEstado() == 3) { %>
                                        Rechazado
                                    <% } else if (prestamo.getEstado() == 4){ %>
                                        Pago 
                                    <%} %>
                                </td>
                            </tr>
                    <% }
                    } %>
                </tbody>
            </table>
        </form>
    </div>
</body>
</html>