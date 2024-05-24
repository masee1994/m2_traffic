<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		table {
			width: 100%;
			border-collapse: collapse;
		}
		th, td {
			border: 1px solid #ddd;
			text-align: center;
			padding: 8px;
		}
		th {
			background-color: #f2f2f2;
		}
		.status-wonhwal {
			color: green;
		}
		.status-seohaeng {
			color: orange;
		}
		.status-gyeongje {
			color: red;
		}
	</style>
</head>
<body>

<table>
	<thead>
	<tr>
		<th style="color: #333333;">(km/h)</th>
		<th class="status-wonhwal">원활</th>
		<th class="status-seohaeng">서행</th>
		<th class="status-gyeongje">정체</th>
		<th style="color: #333333;">정보없음</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td>일반도로</td>
		<td>25이상</td>
		<td>15~25</td>
		<td>15미만</td>
		<td>-</td>
	</tr>
	<tr>
		<td>도시고속</td>
		<td>50이상</td>
		<td>30~50</td>
		<td>30미만</td>
		<td>-</td>
	</tr>
	</tbody>
</table>

</body>
</html>