<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Movimiento"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@page import="Dominio.Movimiento"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalle de Transferencia</title>
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
        .tabla {
            width: 100%;
            margin-bottom: 20px;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }

        .tabla th, .tabla td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
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
<h1 style="text-align:center;">Detalle de transferencia</h1>
<form>
<%		Movimiento mov = new Movimiento();
		if(session.getAttribute("ulttrasnfer")!= null){ 
		mov = (Movimiento) session.getAttribute("ulttrasnfer");%>
				<%	} 
		else
		{%>
		No se ha realizado una transferencia ultimamente
		<%} %>
<table>
<tr><td><p>Cbu destino:</p></td><td><%=mov.getCbuDestino().getCbu()%></td></tr>
<tr><td><p>Importe:</p></td><td><%=mov.getImporte()%></td></tr>
<tr><td><p>Concepto:</p></td><td><%=mov.getDetalle()%></td></tr>
<tr><td><p>Fecha:</p></td><td><%=mov.getFecha()%></td></tr>
<tr><td><p>Nro Movimiento:</p></td><td><%=mov.getMovimiento()%></td></tr>
</table>




<br>
<input type="submit" value="Volver" name="Volverbtn" style="width: 196px; ">

            <p><b>Informacion del Transferencia:</b>
                        <% if(request.getAttribute("error") != null) { %>
                    <p style="color: red;"><%= request.getAttribute("error") %></p>
                <% } %></p>
             
             <table class="tabla">
            <tr>
                <td><img class= "info-img" src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Minimalist_info_Icon.png/600px-Minimalist_info_Icon.png" ></td>
                <td>Una transferencia bancaria es un movimiento de dinero entre dos cuentas bancarias. Esta transacción permite a una persona enviar dinero desde su cuenta bancaria a la cuenta de otra persona.</td>
            </tr>
        </table>
        </form>

</div>
</body>
</html>