<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Prestamos"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestorPrestamos</title>
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
	<% Cliente cli = new Cliente();
		Prestamos pres = new Prestamos();
		if(session.getAttribute("cliente")!= null){ 
		cli = (Cliente) session.getAttribute("cliente");%>
		<%	
		} 
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
 <form action="servletPrestamosCliente" method="doGet" class="search-form">
   
    <h2>Gestor Prestamos</h2>
	<table border="1">
	<%int aprobados=0;
	int rechazados=0;
	int pendientes=0;
	int pagos=0;
	if((request.getAttribute("Rechazados")!=null)&&(request.getAttribute("Aprobados")!=null)&&(request.getAttribute("Pendientes")!=null)&&(request.getAttribute("Pagos")!=null)){
		aprobados=Integer.parseInt(request.getAttribute("Aprobados").toString());
		rechazados=Integer.parseInt(request.getAttribute("Rechazados").toString());
		pendientes=Integer.parseInt(request.getAttribute("Pendientes").toString());
		pagos=Integer.parseInt(request.getAttribute("Pagos").toString());
		
	}%>
	<tr><td><p>Aprobados:</p></td><td><%=aprobados %></td> 
		<td><p>Rechazados:</p></td><td><%=rechazados %></td> 
		<td><p>Pendientes:</p></td><td><%=pendientes %></td>
		<td><p>Pagos:</p></td><td><%=pagos %></td>  
		</tr>
</table><br>
	<table border="1" id="table_id">
	<thead>
    <tr>
  
        <th>N Prestamo</th>
        <th>Fecha</th>
        <th>Importe Solicitado</th>
        <th>Importe con Intereses</th>
        <th>Plazo</th>
        <th>Cuotas</th>
        <th>Monto Cuotas</th>
        <th>Cuotas Pagadas</th>
        <th>Cuenta a Depositar</th>
        <th>Estado</th>
     </tr>
    	</thead>	
    
    	<%
    	ArrayList<Prestamos> pres1 = new ArrayList<Prestamos>();
    		if(request.getAttribute("prestamosList")!=null){
    	 pres1 = (ArrayList<Prestamos>)request.getAttribute("prestamosList");
    	    	 
    		}
    	%>
  
  		<tbody>
    	 <% for(Prestamos p : pres1){  %>
    	 	
    		<tr><td><p><%=p.getPrestamo() %></p></td>
    		<td><p><%=p.getFecha() %></p></td> 
    		<td><p><%=p.getImporteSolicitado()%></p></td> 
    		<td><p><%=p.getImporteConInteres() %></p></td> 
    		<td><p><%=p.getPlazo() %></p></td> 
    		<td><p><%=p.getCuotas() %></p></td> 
    		<td><p><%=p.getMontoCuotas()%></p></td> 
    		<td><p><%=p.getCuotasPagadas()%></p></td> 
    		<td><p><%=p.getCuentaADepositar().getCuenta() %></p></td> 
    		<td><p>
    			<% if (p.getEstado() == 1) { %>
                   Pendiente
                <% } else if (p.getEstado() == 2) { %>
                   Aprobado
                <% } else if (p.getEstado() == 3) { %>
                   Rechazado
                <% } else if (p.getEstado() == 4){ %>
                   Pago 
                <%} %></p></td>
    		</tr>
    		 <% } %>
          </tbody>
     
  	
  	
	 </table><br>  
</div>

</body>
</html>