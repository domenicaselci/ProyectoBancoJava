<%@page import="Dominio.Cliente"%>
<%@page import="Dominio.Cuenta"%>
<%@ page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Transferencias</title>
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
    
   
    <script>
        function confirmarTransferencia() {
            return confirm('¿Estás seguro de que deseas transferir?');
        }
    </script>
</head>
<body>
<%
    Cliente cli = new Cliente();
    if (session.getAttribute("cliente") != null) {
        cli = (Cliente) session.getAttribute("cliente");
%>
<%
    } else {
%>
    No se ha iniciado sesión
<%
    }
%>
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
    <h1 style="text-align:center;">Transferencias</h1>
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
			<h2 style="text-align:center;color:red;">DEBE POSEER AL MENOS UNA CUENTA PARA TRANSFERIR</h2>
			<%}else{%>
    <form action="servletTransferencias" method="post" onsubmit="return confirmarTransferencia();">
        <%
            ArrayList<Cuenta> listaCuentas = (ArrayList<Cuenta>) request.getAttribute("listaCuentas");
        %>
        <table>
            <tr>
                <td><p>Elegir cuenta:</p></td>
                <td>
                    <select id="dc1" style="width: 210px; height: 31px;" name="CuentaDisponibles">
                        <% if (listaCuentas != null) {
                            for (Cuenta cu : listaCuentas) {
                                
                                if (cu.isEstado() == true) { %>
                                    <option value="<%=cu.getCuenta()%>">
                                        <%= "Cuenta " + cu.getCuenta() + " - Saldo: $" + cu.getSaldo()%>
                                    </option>
                                <% }
                            }
                        } %>
                    </select>
                </td>
            </tr>
        </table>
        <br><br>

        <table>
            <tr><td><p>CBU: </p></td><td><input type="number" required name="CBU" style="width: 200px;"></td></tr>
            <tr><td><p>IMPORTE:</p></td><td><input type="text" pattern=[0-9]+([,\.][0-9]+)? required name="Importe" style="width: 202px;"></td></tr>
            <tr>
                <td><p>CONCEPTO:</p></td>
                <td>
                    <input type="text" required name="Concepto" style="width: 201px;">
                    <input type="submit" value="Transferir" name="TransferirBtn" style="width: 178px;">
                </td>
            </tr>
        </table>

        <p><b>Informacion del Transferencia:</b>
            <% if(request.getAttribute("error") != null) { %>
                <p style="color: red;"><%= request.getAttribute("error") %></p>
            <% }}} %></p>
            
         
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