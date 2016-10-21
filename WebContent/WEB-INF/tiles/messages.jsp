<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>


<div id="messages"></div>


<script type="text/javascript">

	var timer;
	
	$(function () {
		  var token = $("meta[name='_csrf']").attr("content");
		  var header = $("meta[name='_csrf_header']").attr("content");
		  $(document).ajaxSend(function(e, xhr, options) {
		    xhr.setRequestHeader(header, token);
		  });
		});

	function showReply(i) 
	{
		stopTimer();
		$("#form" + i).toggle();
	}
	
	function success(data) 
	{
		$("#form" + data.target).toggle();
		$("#alert" + data.target).text("Message Sent");
		startTimer();
	}
	
	function error(data) 
	{
		alert("Error sending message");
	}

	function sendMessage(i, name, email) 
	{
		var text = $("#textbox" + i).val();

		$.ajax({
			"type" : 'POST',
			"url" : '<c:url value="/sendMessage" />',
			"data" : JSON.stringify({"target" : i, "text" : text, "name" : name,"email" : email }),
			"success" : success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});
	}

	function showMessages(data) 
	{
		$("#messages").html("");

		var message = null;
		var messageDiv = null;
		var subjectSpan = null;
		var contentSpan = null;
		var nameSpan = null;
		var replyForm = null;
		var textarea = null;
		var replyButton = null;
		var alertSpan = null;
		

		for (var i = 0; i < data.messages.length; i++) {
			message = data.messages[i];

			messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");

			subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));

			contentSpan = document.createElement("span");
			contentSpan.setAttribute("class", "messageBody");
			contentSpan.appendChild(document.createTextNode(message.content));

			nameSpan = document.createElement("span");
			nameSpan.setAttribute("class", "name");
			nameSpan.appendChild(document.createTextNode(message.name + " ("));

			link = document.createElement("a");
			link.setAttribute("class", "replyLink");
			link.setAttribute("href", "#");
			link.setAttribute("onclick", "showReply(" + i + ")");
			link.appendChild(document.createTextNode(message.email));
			nameSpan.appendChild(link);
			nameSpan.appendChild(document.createTextNode(")"));
			
			alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alert");
			alertSpan.setAttribute("id", "alert" + i);
			

			replyForm = document.createElement("form");
			replyForm.setAttribute("class", "replyForm");
			replyForm.setAttribute("id", "form" + i);

			textarea = document.createElement("textarea");
			textarea.setAttribute("class", "replyarea");
			textarea.setAttribute("id", "textbox" + i);

			replyButton = document.createElement("input");
			replyButton.setAttribute("class", "replyButton");
			replyButton.setAttribute("type", "button");
			replyButton.setAttribute("value", "Reply");
			replyButton.onclick = function(j, name, email) {
				return function() {
					sendMessage(j, name, email);
				}
			}(i, message.name, message.email);

			replyForm.appendChild(textarea);
			replyForm.appendChild(replyButton);

			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(contentSpan);
			messageDiv.appendChild(nameSpan);
			messageDiv.appendChild(alertSpan);
			messageDiv.appendChild(replyForm);

			$("#messages").append(messageDiv);
		}
	}

	function onLoad() {
		updatePage();
		startTimer();
	}

	function startTimer() {
		timer = window.setInterval(updatePage, 5000);
	}

	function stopTimer() {
		window.clearInterval(timer);
	}

	function updatePage() {
		$.getJSON("<c:url value="/getMessages"/>", showMessages);
	}

	$(document).ready(onLoad);
</script>