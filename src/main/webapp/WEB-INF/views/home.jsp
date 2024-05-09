<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="test">test</a>

<c:forEach items="${info}" var="dao">
	<div>${dao.spot_num}</div>
	<div>${dao.spot_nm}</div>
	<div>${dao.grs80tm_x}</div>
	<div>${dao.grs80tm_y}</div>
</c:forEach>
</body>
</html>
