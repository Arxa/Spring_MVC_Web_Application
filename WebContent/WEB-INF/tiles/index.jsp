<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table class="offerstable" cellspacing="10">
	<tr>
		<td><b><u>Name</u></b></td>
		<td><b><u>Email</u></b></td>
		<td><b><u>Offer</u></b></td>
	</tr>
	<c:forEach var="offer" items="${offers}">
		<tr>
			<td><c:out value="${offer.user.name}"></c:out></td>
			
			<td> 
				<c:choose>
					<c:when test="${user!=offer.username}"> <a href="<c:url value='/message?uid=${offer.username}'></c:url>">Contact</a> </c:when>  
					<c:otherwise><c:out value="${offer.user.email}"></c:out></c:otherwise>
				</c:choose>
			</td>
			
			<td><c:out value="${offer.text}"></c:out></td>
		</tr>
	</c:forEach>
</table>

<br />



<script type="text/javascript">

	function updateMessageLink(data)
	{
		$("#numberMessages").text(data.number);
	}

	function onLoad()
	{
		updatePage();
		window.setInterval(updatePage, 5000);
	}
	
	function updatePage()
	{
		$.getJSON("<c:url value="/getMessages"/>", updateMessageLink);
	}
	
	$(document).ready(onLoad);

</script>













