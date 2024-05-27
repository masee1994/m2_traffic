<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<title>Weather and Traffic Info</title>
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
<div id="traffic-info">
	<p>Loading traffic data...</p>
</div>

<script type="text/javascript">
	function getSpeedColor(speed) {
		if (speed >= 25) {
			return '#008000'; // 초록색
		} else if (speed >= 15 && speed < 25) {
			return '#FFA500'; // 주황색
		} else if (speed < 15) {
			return '#FF0000'; // 빨간색
		} else {
			return 'black'; // 정보없음
		}
	}

	$(document).ready(function() {
		// Fetch weather data
		$.ajax({
			url: 'test1',
			method: 'GET',
			dataType: 'json',
			success: function(data) {
				$('#weather-info').html(
						'<p>날씨 : <strong style="color: #4E73DF">' + data.weatherDescription + ' (' + data.weatherMain + ')</strong></p>' +
						'<p>기온 : <strong style="color: #FF0000">' + data.tempCelsius + ' °C</strong></p>' +
						'<p>풍속 : <strong style="color: #1C1A1AFF">' + data.windSpeed + ' m/s</strong></p>' +
						'<p>풍량 : <strong style="color: #1C1A1AFF">' + data.windDeg + '° <span id="arrow-container"><span id="arrow"></span></span></strong></p>'
				);
				$('#arrow-container').css('transform', 'rotate(' + data.windDeg + 'deg)');
			},
			error: function(xhr, status, error) {
				$('#weather-info').html('<p>Error fetching weather data.</p>');
			}
		});

		// Fetch traffic data
		$.ajax({
			url: 'trafficStats',
			method: 'GET',
			dataType: 'json',
			success: function(data) {
				var spdStat1 = parseFloat(data.spdStat1);
				var spdStat2 = parseFloat(data.spdStat2);
				var spdStat1Color = getSpeedColor(spdStat1);
				var spdStat2Color = getSpeedColor(spdStat2);

				$('#traffic-info').html(
						'<p>서울시 전체속도 : <strong style="color: ' + spdStat1Color + '">' + spdStat1 + ' km/h</strong></p>' +
						'<p>서울시 전체속도 상세 : <strong style="color: ' + spdStat1Color + '">' + data.spdStat1Det + '</strong></p>' +
						'<p>도심 전체속도 : <strong style="color: ' + spdStat2Color + '">' + spdStat2 + ' km/h</strong></p>' +
						'<p>도심 전체속도 상세 : <strong style="color: ' + spdStat2Color + '">' + data.spdStat2Det + '</strong></p>'
				);
			},
			error: function(xhr, status, error) {
				$('#traffic-info').html('<p>Error fetching traffic data.</p>');
			}
		});
	});
</script>
</body>
</html>
