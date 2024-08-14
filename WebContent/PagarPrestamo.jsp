<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Cuenta"%>
<%@page import="Dominio.Prestamos"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PagarPrestamo</title>
    <link  rel="stylesheet" type="text/css" href="Estilos.css"/>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>
    <script>
        function confirmarPago() {
            return confirm('¿Estás seguro de que deseas abonar esta cuota?');
        }
    </script>
</head>
<body>
<%		Cliente cli = new Cliente();
		
		if(session.getAttribute("cliente")!= null){ 
		cli = (Cliente) session.getAttribute("cliente");
		 }%>
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
<form action="servletPrestamosCliente" method="post">
	<h1 style="text-align:center;">Pagar Prestamo</h1>
	<%
	Prestamos pre = (Prestamos)request.getAttribute("presta");
	Boolean cue = (Boolean)request.getAttribute("resto");
	Boolean nuevo = (Boolean)request.getAttribute("nuevo");
	ArrayList<Prestamos> listapres = (ArrayList<Prestamos>) request.getAttribute("listaprestamos");
	ArrayList<Cuenta> listacuentas = (ArrayList<Cuenta>) request.getAttribute("listacuentas");
	if(session.getAttribute("cuentas")!= null){ 
			
			int cont=0;
			for(Cuenta c:listacuentas){
				cont=cont+1;
			}
			if(cont==0){
			%>
			<h2 style="text-align:center;color:red;">DEBE POSEER AL MENOS UNA CUENTA Y UN PRESTAMO ACTIVO</h2>
			<%}else{

			%>
	<table>
	<tr><td style="width: 101px; "><p>Elegir Cuenta</p></td><td>
	<Select name="Cuenta" style="width: 200px;">
		<%
		
		if (listacuentas != null){
			for (Cuenta u: listacuentas){ 
				if(u.isEstado()){
			
		%>  
			<option value="<%=u.getCuenta()%>"> Cuenta: <%=u.getCuenta()%> + Saldo: <%=u.getSaldo()%></option>
		<%	
		
		}}
		}%>
   </Select>
	<tr><td style="width: 101px; "><p>Cuota a Pagar:</p></td><td>
	<Select name="Prestamo" style="width: 200px;">
		<%
		if (listapres != null){
			for (Prestamos p: listapres){ 
			
		%>  
			<option value="<%=p.getPrestamo()%>"> <%=p.getMontoCuotas()%></option>
		<%	
		
		}
		}%>
   </Select>
   </table>
   <% 
	if(request.getAttribute("SinPrestamos")!=null){
		%><h4 style="color:red;text-align:center;"> No tiene ningún préstamo a pagar</h4><%
			}else{%>
   	<div class="center-button" style="margin:10px">
        <input type="submit" name="btnPagar" value="Pagar" onclick="return confirmarPago();" style="text-align:center;width: 40%;height:40px;">
   	</div>
	<table class="message-table">
    <% 
    Boolean agregado = (Boolean)request.getAttribute("modifico");
    if (agregado != null) {
        if (agregado) {
    %> 
            <tr>
                <td colspan="2">Pagaste la cuota N°: <%= pre.getCuotasPagadas() %></td>
            </tr>
    <%
        } else {
            if (nuevo) {
    %>      
                <tr>
                    <td colspan="2">No tenés suficiente dinero en cuenta</td>
                </tr>
    <%
            } else {
    %>
                <tr>
                    <td colspan="2">¡Tu préstamo está pago!</td>
                </tr>
    <%
            }
        }
    }}}
    %>
</table>
<%} %>
    </form>
</div>
</body>
</html>