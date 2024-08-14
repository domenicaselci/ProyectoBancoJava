<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Cuenta"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inicio</title>
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
		<form action="servletMenuCliente" method="post">
		<%		ArrayList<Cuenta> listaC;
		if(session.getAttribute("cuentas")!= null){ 
		listaC = (ArrayList<Cuenta>) session.getAttribute("cuentas");
		int cont=0;
		
		
		%>
		
	 
		    <h1 style="text-align:center;font-size:50px;">Hola <%= cli.getNombre() %>!</h1>
			<table style="text-align: center;width:100%;border-spacing:15px ">
			<%for(Cuenta a:listaC){
				 cont=cont+1;
			%>
			
				<tr><td><input type="submit" name="btnCuenta<%=cont%>" value="Cuenta <%=a.getCuenta()%>"style="width: 80%;height:90px;font-size:20px"></td> </tr> 
		<% } }%>
		
			</table>
			<br>
			</form>
			<form action="servletTransferencias?Param=1">
			<table style="text-align: center;width:100%;border-spacing:15px ">
				<tr><td><input type="submit" name="btnRealizarTra" value="Realizar Transferencia" style="width: 40%;height:90px;font-size:15px;border-radius: 10px"></td> </tr> 			
			</table>
			
		</form>
	</div>
</body>
</html>