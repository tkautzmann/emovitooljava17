<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/estilos.css" />
		<title>Emovitool</title>
	</head>
	<body>
	
		<div class="menu">
		
			<c:url var="logoutUrl" value="/logout" />
   			<form class="logout" action="${logoutUrl}" method="post">
     			<input type="submit" value="Log out" />
     			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   			</form><br />
			
			<a href="chooseCoder?id_study=${study.id}">&lt;&lt; Voltar</a><br /><br />
		
			<h3>
				Selected Study: ${study.description} <br /><br />
				Selected Coder: ${coder.nome}
			</h3>
			
			<br /><br />
		
			<h2>Choose the recording session</h2>
			
			<div class="options">
			
				<c:forEach var="record_session" items="${record_sessions}">
				    
				    <a href="openRecordSession?id_record_session=${record_session.id}&id_coder=${coder.id}">
				    	Session id: ${record_session.id}
				    </a>
				    
				</c:forEach>
			
			</div>
			
		</div>
		
	</body>
</html>