<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script type="text/javascript">
		$(document).ready(function(){
			document.f.username.focus();
		});
	</script>
	
	<h3>Login with Username and Password</h3>
	
	<c:if test = "${param.error != null}">
		<p class="error"> Login failed. Wrong username or password. </p>
	</c:if>
	
	<form name='f' action='/offers/login' method='POST'>
		<table class=formtable>
			<tr>
				<td>Username:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td>Remember me:</td>
				<td><input type='checkbox' name='remember-me' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</table>
	</form>
	
	<p> <a href="<c:url value="/newAccount"/>">Create new Account</a> </p>
	


