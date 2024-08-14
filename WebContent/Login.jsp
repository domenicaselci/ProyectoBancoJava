<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
	<style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        .content {
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            margin:0;
            
        }
        #IDcuadroCentral{
		width:100%;
		margin:auto;
		border:1px solid black;
		padding:30px;
		text-align:center;
		}
    </style>
    
</head>

<body>
	<div class="content">
		<form action="servletLogin" method="post" style="text-align:center">
			<div ID=IDcuadroCentral>
				<h1 style="text-align:center;font-size:50px;">Login</h1>
				<table style="text-align: center;width:100%;border-spacing:15px ">
					<tr><td>Usuario</td> <td><input type="text" name="txtUsuario" style="width: 80%;"></td> </tr> 
					<tr><td>Contraseña</td> <td><input type="password" name="txtContrasena" style="width: 80%;"></td> </tr> 
				</table>
				
				<input type="submit" name="btnIngresar" value="Ingresar" style="width: 50%;height:30px;font-size:15px;">
				 <% if(request.getAttribute("error") != null) { %>
                    <p style="color: red;"><%= request.getAttribute("error") %></p>
                <% } %>
			</div>
		</form>
	</div>
</body>
</html>