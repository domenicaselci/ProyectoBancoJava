<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inicio Admin</title>

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
    </style>
    
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
	<h1 style="text-align: center;"><big>Hola admin!</big></h1>
	<form action="servletMenus" method="get">
		<table style="width:100%;">
		<tr>
			<td style="text-align:right;"><input type="submit" value="Administrar Clientes" name="btnAdministarClientes" style="width: 198px; height: 50px; "></td> 
			<td style="text-align:left;"><input type="submit" value="Administrar Cuentas" name="btnAdministarCuentas" style="width: 198px; height: 50px; "> </td> </tr>
		<tr>
			<td style="text-align:right;"><input type="submit" value="Autorizar Prestamos" name="btnAutorizarPrestamos" style="width: 198px; height: 50px; "> </td>  
			<td style="text-align:left;"><input type="submit" value=" Informes y Reportes" name="btnInfoReportes" style="width: 198px; height: 50px; "> </td> </tr>
		</table>
	</form>
</div>
</body>
</html>