<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<title>Insert title here</title>
</head>
<style>
	/* 화살표 스타일 */
	#arrow {
		display: inline-block;
		width: 0;
		height: 0;
		border-left: 10px solid transparent;
		border-right: 10px solid transparent;
		border-bottom: 20px solid #333333;
		transform: rotate(90deg); /* 기본 회전 방향 */
	}
	/* 화살표 회전 컨테이너 */
	#arrow-container {
		display: inline-block;
		transform-origin: center center;
		margin-left: 5px;
	}
</style>
</head>
<body>
<div id="weather-info">
	<p>Loading weather data...</p>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			url: 'test1',
			method: 'GET',
			dataType: 'json',
			success: function(data) {
				$('#weather-info').html(
						'<p>날씨 : <strong style="color: #4E73DF">' + data.weatherDescription + ' (' + data.weatherMain + ')</strong></p>' +
						'<p>기온 : <strong style="color: #FF0000">' + data.tempCelsius + ' °C</strong></p>' +
						'<p>풍속 : <strong style="color: #008000">' + data.windSpeed + ' m/s</strong></p>' +
						'<p>풍량 : <strong style="color: #FFA500">' + data.windDeg + '° <span id="arrow-container"><span id="arrow"></span></span></strong></p>'
				);
				$('#arrow-container').css('transform', 'rotate(' + data.windDeg + 'deg)');
			},
			error: function(xhr, status, error) {
				$('#weather-info').html('<p>Error fetching weather data.</p>');
			}
		});
	});
</script>
</body>
</html>