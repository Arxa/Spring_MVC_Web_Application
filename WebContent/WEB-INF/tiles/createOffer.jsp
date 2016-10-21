<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	
	function onDeleteClick(event)
	{
		var doDelete = confirm("Are you sure you want to delete this offer?");
		if(doDelete == false)
		{
			event.preventDefault(); // Prevents button that was clicked from performing it's action.
		}
	}
		
	function onReady()
	{
		$("#delete").click(onDeleteClick);
	}
	
	$(document).ready(onReady);


</script>

	<sf:form method="post" action="${pageContext.request.contextPath}/iscreated" commandName="offer">
	<sf:input type="hidden" name="id" path="id"/>
		<table class="formtable">
			<tr> <td class="label">Your offer: </td> <td> <sf:textarea class="control" path="text" name="text" rows="10" cols="10"></sf:textarea> <br/> <sf:errors path="text" cssClass="error"></sf:errors> </td> </tr>
			<tr> <td class="label"></td> <td> <input class="control" value="Save advert" type="submit" /> </td> </tr>
			
			<c:if test="${offer.id != 0}">
				<tr> <td class="label"></td> <td>&nbsp;</td></tr>
				<tr> <td class="label"></td> <td> <input class="delete control" id="delete" name="delete" value="Delete advert" type="submit" /> </td> </tr>
			</c:if>
		</table>	
	</sf:form>

