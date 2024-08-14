<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Cuenta"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prestamos</title>
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

        .content h2 {
            margin-bottom: 20px;
            font-size: 32px;
            color: #333;
        }


        table {
            width: 100%;
            margin-bottom: 20px;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }

        table th, table td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

		 input[type="submit"] {
            width: 250px;
            padding: 15px;
            font-size: 18px;
            background-color: #333;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
            display: block;
            margin: 0 auto;
        }

        input[type="submit"]:hover {
            background-color: #555;
        }
        
        .info-img {
            width: 106px;
            height: 112px;
            display: block;
            margin: 0 auto;
        }
    </style>
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
    <h2>Prestamos</h2>

    <form action="servletPrestamosCliente" method="get">
    <table>
            <tr>
                <td><input type="submit" name="btnSolicitarPrestamo" value="Solicitar Prestamo"></td>
            </tr>

            <tr>
                <td><input type="submit" name="btnPagarPrestamo" value="Pagar Prestamo"></td>
            </tr>
   
            <tr>
                <td><input type="submit" name="btnGestionarPrestamo" value="Gestionar Prestamos"></td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td><img class="info-img" src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Minimalist_info_Icon.png/600px-Minimalist_info_Icon.png"></td>
            <td>Un préstamo es un producto financiero que permite a un usuario acceder a una cantidad fija de dinero al comienzo de la operación, con la condición devolver esa cantidad más los intereses pactados en un plazo determinado.</td>
        </tr>
    </table>
</div>

</body>
</html>
