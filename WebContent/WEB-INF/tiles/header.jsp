<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<a class="title" href="<c:url value='/'/>">Offers</a>

<c:choose>
	<c:when test="${online}">
		<span class="loggedInAs"> <c:out value="Logged in as: ${user}"></c:out></span>
	</c:when>
	<c:otherwise>
		<a class="register" href="<c:url value='/register'/>">Register</a>
	</c:otherwise>
</c:choose>



<sec:authorize access="!isAuthenticated()">
	<a class="login" href="<c:url value='/login'/>">Log in</a>
</sec:authorize>


<sec:authorize access="isAuthenticated()">
	<a class="login" href="<c:url value='/logout'/>">Log out</a>
</sec:authorize>
