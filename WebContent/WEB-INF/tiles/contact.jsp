<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    


	<h2>Send Message</h2>
	
	<sf:form method="post" commandName="message">
	<!-- Web Flow Configurations -->
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>
	<input type="hidden" name="_eventId" value="send"/>
	<!-- /Web Flow Configurations -->
	
		<table class="formtable">
		
			<tr> <td class="label">Your name: </td> <td> <sf:input class="control" path="name" type="text" value="${fromName}"/> <br/> <div class="error"> <sf:errors path="name"></sf:errors></div> </td> </tr>
			<tr> <td class="label">Your email: </td> <td> <sf:input class="control" path="email" type="text" value="${fromEmail}"/> <br/> <div class="error"> <sf:errors path="email"></sf:errors></div> </td> </tr>
			
			<tr> <td class="label">Subject: </td> <td> <sf:input class="control" path="subject" type="text"/> <br/> <div class="error"> <sf:errors path="subject"></sf:errors></div> </td> </tr>
			<tr> <td class="label">You message: </td> <td> <sf:textarea class="control" path="content" type="text"/> <br/> <div class="error"> <sf:errors path="content"></sf:errors></div> </td> </tr>
			
			<tr> <td class="label"></td> <td> <input class="control" value="Send Message" type="submit" /> </td> </tr>
		</table>	
	</sf:form>
