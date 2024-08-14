<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Movimiento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@page import="Dominio.Cuenta"%>
     <%@page import="Dominio.Movimiento"%>
   <%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movimientos</title>
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
<style >
	.mensajeError{
    	color:red;
    	text-align:center;
    }
    .mensajeExitoso{
    	text-align:center;}
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
		<form action="servletMovimiento" method="post">
			
			<div class="banner">
			<%Cuenta cue=new Cuenta();
			if(session.getAttribute("cuenta")!= null){
				cue=(Cuenta)session.getAttribute("cuenta");
				%>
			
				<table style="text-align:center;" class="table2" >
					<tr><td colspan="3"><h1>Tipo de cuenta: <%= cue.getTipoDeCuenta().getDescripcion()%></h1></td></tr>
					<tr><td ><h3> Numero de cuenta: <%=cue.getCuenta() %></h3></td><td><h3>CBU:<%= cue.getCbu() %></h3></td><td><h3>Fecha de creacion: <%=cue.getFechadeCreacion() %></h3></td></tr>
					<tr><td colspan="3"><h1>SALDO: $ <%=cue.getSaldo() %></h1></td></tr>
				</table>
				
				<%} %>
				
			</div>
			
			<div>
			<table>
			<tr><td colspan=2 style="text-align:center;">Filtrar por Monto</td>
				<td colspan=2 style="text-align:center;">Filtrar por Fecha</td></tr>
		
			<tr><td>Desde: </td><td><input type="text" pattern=^-?[0-9]+([,.][0-9]+)?$  name="InicioMonto"></td>
				<td>Desde: </td><td><input type="Date"  name="InicioFecha"></td>
				</tr>
			
			<tr><td>Hasta: </td><td><input type="text" pattern=^-?[0-9]+([,.][0-9]+)?$  name="FinalMonto"></td>
				<td>Hasta: </td><td><input type="Date"  name="FinalFecha"></td>
				</tr>
				
			<tr><td colspan=2 style="text-align:center;"><input type="submit" name="btnAplicarFiltro" style="width:20%"></td>
				<td colspan=2 style="text-align:center;"><input type="submit" name="btnAplicarFiltroFechas" style="width:20%"></td>
			</table>
			<table>
			<tr><td>Buscar por Detalle</td>
				<td><input type="Text"  name="DetalleLike"></td>
				<td><input type="submit" name="BtnDetalleLike"></td>
			</table>
			
			<table style="border:0;width:100%;">
			<tr><td style="text-align:center;"><input type="submit" name="btnMostarTodos" value="Mostrar todos los Movimientos" style="width:25%" ></td></tr>
			</table>
			
			<input type="hidden" name="cuenta" value=<%= cue.getCuenta() %>>
			
			
			
			
			
			<%
			if(request.getAttribute("CompletarFiltros")!=null){
				%>
				<h4 class="mensajeError">Debe completar ambos campos del filtro</h4>
				<%
			}
			
			%>
			
			
			</div>
			
			
		
				<h1 style="text-align:center;">Movimientos</h1>
				
				<%
				ArrayList<Movimiento> Lmovimiento=new ArrayList<Movimiento>();
				
				if(request.getAttribute("MovimientoLikeDetalle")!=null){
					
					Lmovimiento=(ArrayList<Movimiento>)request.getAttribute("MovimientoLikeDetalle");
					
				}
				else if(request.getAttribute("MovimientoFiltro")!=null ){
				
					Lmovimiento=(ArrayList<Movimiento>)request.getAttribute("MovimientoFiltro");
					%>
				
				
				<% }
				else if(request.getAttribute("MovimientoFiltroFechas")!=null){
					Lmovimiento=(ArrayList<Movimiento>)request.getAttribute("MovimientoFiltroFechas");
					
				}
				
				else if(session.getAttribute("movimiento")!= null){
				
				
				Lmovimiento=(ArrayList<Movimiento>)session.getAttribute("movimiento");
				}
				%>
			
				<table border="1" id="table_id">
				<thead>
				<tr>
						<th>Nro Movimiento: </th>
						<th>Fecha: </th>
						<th>Monto: </th>
						<th>Detalle:</th>
						<th>Tipo de movimiento:</th>
					</tr>
					</thead>
					<tbody>
				<%for(Movimiento a:Lmovimiento){ %>
					
					<tr><td><%=a.getMovimiento() %></td><td><%=a.getFecha() %></td><td><%=a.getImporte() %></td><td><%=a.getDetalle() %></td>
					<td><%= a.getTipoMovimiento().getDescripcion() %></td></tr>
					<% }%>
					</tbody>
				</table>
			</div>
			
		
		</form>
	</div>


</body>
</html>