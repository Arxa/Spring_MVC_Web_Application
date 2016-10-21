<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">

	function onLoad() {
		$("#password").keyup(checkPasswords);
		$("#confirmpass").keyup(checkPasswords);

		$("#details").submit(canSubmit);
	}

	function canSubmit() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password != confirmpass) {
			alert("<fmt:message key='UnmatchedPasswords.user.password'/>");
			return false;
		} else {
			return true;
		}
	}

	function checkPasswords() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (confirmpass.length == 0) {
			return;
		}

		if (password == confirmpass) {
			$("#matchpass").text(
					"<fmt:message key='MatchedPasswords.user.password'/>");
			$("#matchpass").addClass("valid");
			$("#matchpass").removeClass("error");
		} else {
			$("#matchpass").text(
					"<fmt:message key='UnmatchedPasswords.user.password'/>");
			$("#matchpass").addClass("error");
			$("#matchpass").removeClass("valid");
		}
	}

	$(document).ready(onLoad);
	
</script>

