<%@page import="Dominio.Cuenta"%>
<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Prestamos"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SolicitarPrestamo</title>
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

        .sidebar a, .sidebar h3 {
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
        
        input[type="submit"]:hover{
        	background-color: #555;
        	color: white;
        }
        
        .mensajeError{
    	color:red;
    	text-align:center;
    	}
   		.mensajeExitoso{
   	 	text-align:center;}
        
    </style>
    
        <script>
        function confirmarSolicitud() {
            return confirm('¿Estás seguro de que deseas solicitar este Prestamo?');
        }
    </script>
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
    <h1 style="text-align:center;font-size:50px;">Solicitar Prestamo</h1>
    <%
    	ArrayList<Cuenta> listaC=new ArrayList<Cuenta>();
		if(session.getAttribute("cuentas")!= null){ 
			listaC= (ArrayList<Cuenta>) session.getAttribute("cuentas");
			int cont=0;
			for(Cuenta c:listaC){
				cont=cont+1;
			}
			if(cont==0){
			%>
			<h2 style="text-align:center;color:red;">DEBE POSEER AL MENOS UNA CUENTA PARA PODER SOLICITAR UN PRESTAMO</h2>
			<%}else{
				Boolean pedido;
				if(request.getAttribute("pedido")==null){
			
			%>
			
			
	<form action="servletPrestamosCliente" method="post">	
		<table>
		<tr><td><p>Importe Solicitado: </p></td><td style="text-align:right"><input type="number" name="ImporteSolicitado" style="width:100%"></td> 
			<td style="width:20%"></td>
			<% if((session.getAttribute("prestSolicitar")!=null)&&(request.getAttribute("cuentaDepositar")!=null)){ %>
			<td colspan=2 style="width:40%;text-align:center;"><h3>PRESTAMO A SOLICITAR</h3></td><%} %>
		</tr>
		<tr><td><p>Plazo:</p></td>
			<td style="text-align:right">
				<select name= "sPlazo" required style="width:100%">	
					<option value=3> 3 meses </option>
					<option value=6> 6 meses </option>
					<option value=12> 12 meses </option>
					<option value=18> 18 meses </option>
					</select> </td>
					<td></td> 
			<% 
			Prestamos prestamoSoli=new Prestamos();
			int cuentaDepositar=0;
			if((session.getAttribute("prestSolicitar")!=null)&&(request.getAttribute("cuentaDepositar")!=null)){
				prestamoSoli=(Prestamos)session.getAttribute("prestSolicitar");
				cuentaDepositar=(int)request.getAttribute("cuentaDepositar");
				%>
			<td>TASA DE INTERES: </td>
			<td>65%</td><%} %>
		</tr> 
		<tr><td><p>Cuenta a depositar pago:</p></td>
			<td style="text-align:right">
				<select name= "opcuentas" style="width:100%">	
				<% for(Cuenta c:listaC){
					if(c.isEstado()){
						%>
						<option value=<%=c.getCuenta()%>><%= "Cuenta " + c.getCuenta()%></option><%
					}
				}
					
					
					%>
					</select></td> 
			<td></td>
			<% if((session.getAttribute("prestSolicitar")!=null)&&(request.getAttribute("cuentaDepositar")!=null)){ %>
			<td><p>Monto Cuota:</p></td>
			<td style="text-align:right">$ <%=prestamoSoli.getMontoCuotas()%></td><%} %>
			</tr>
		<tr><td></td><td style="text-align:right"><input type="submit" name="btnCalcularPrestamo" value="Calcular Cuotas"></td>
			<td></td>
			<% if((session.getAttribute("prestSolicitar")!=null)&&(request.getAttribute("cuentaDepositar")!=null)){ %>
			<td>Importe Solicitado:</td><td style="text-align:right">$ <%=prestamoSoli.getImporteSolicitado() %></td>		
			<%} %>	
		</tr>
		
		<tr><td colspan=2>
			<% 
			if(request.getAttribute("CompletarCampo")!=null){
				%><h4 class="mensajeError"> Debe completar el campo de Importe</h4><%
			} %>
			</td>
			<% if((session.getAttribute("prestSolicitar")!=null)&&(request.getAttribute("cuentaDepositar")!=null)){ %>
			<td></td><td>Importe con Intereses: </td><td style="text-align:right">$ <%=prestamoSoli.getImporteConInteres() %></td></tr>
		<tr><td colspan=3></td><td>Cantidad de cuotas: </td><td style="text-align:right"><%=prestamoSoli.getCuotas() %></td></tr>
		<tr><td colspan=3><input type=hidden name="hCuenta" value=<%=cuentaDepositar %>></td><td>Plazo: </td><td style="text-align:right"><%=prestamoSoli.getPlazo() %> meses</td></tr>
		<tr><td colspan=3></td><td colspan=2><Input type="submit" name="btnPedirPrestamo" value="Solicitar Prestamo" onclick="return confirmarSolicitud();" style="width: 100%; height: 52px"></td></tr>
		<%} %>	
		</table>
		
			
		<%}else{
			pedido=Boolean.parseBoolean(request.getAttribute("pedido").toString());
			if(pedido){
				%>
				<form action="servletPrestamosCliente" method="get">
					<table style="width:100%;text-align:center">
					<tr></tr>
					<tr><td><h2> SE HA COMPLETADO LA SOLICITUD DE PRESTAMO.<br>Puede consultar el estado del mismo en su GESTOR DE PRESTAMOS</h2>
							
						<td></tr>
					<tr></tr>
					<tr></tr>
					<tr><td><input type="submit" name="btnSolicitarPrestamo" value="Solicitar Otro Prestamo" style="width:30%;height:60px;font-size:20px;"><input type="submit" name="btnGestionarPrestamo" value="Ir al GESTOR" style="width:30%;height:60px;font-size:20px;"></td></tr>
					</table>
					
				</form>
				<%
			}else{
				%>
				<form action="servletPrestamosCliente" method="get">
					<table style="width:100%;text-align:center">
					<tr></tr>
					<tr><td><h2> NO SE HA PODIDO SOLICITAR EL PRESTAMO<br>Intente mas tarde.</h2>
							
						<td></tr>
					<tr></tr>
					<tr></tr>
					<tr><td><input type="submit" name="btnSolicitarPrestamo" value="Volver a intentar" style="width:30%;height:60px;font-size:20px;"></td></tr>
					</table>
					
				</form>
				<%
			}
			}
		}}%>
	</form>
    
    
</div>

</body>
</html>