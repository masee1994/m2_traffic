<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>지도에 로드뷰 도로 표시하기</title>

</head>
<style>
    #map { position: relative; width: 100%; height: 300px; }
    .control { position: absolute; top: 10px; right: 10px; z-index: 5; }
    .button { padding: 5px 10px; background-color: white; border: 1px solid black; cursor: pointer; }
    .button:hover { background-color: #f8f8f8; }
</style>
<script type="text/javascript">
    var spots = [
        <c:forEach items="${info}" var="item" varStatus="status">
        {
            num: '${item.spot_num}',
            name: '${item.spot_nm}',
            longitude: '${item.dPx_longitude}',
            latitude: '${item.dPy_latitude}'
        }${not status.last ? ',' : ''}
        </c:forEach>
    ];
</script>
<body>

<div id="map">
    <div class="control" style="right: 120px;">
        <a href="javascript:void(0)" class="button" onclick="toggleTraffic()" title="일반지도1">교통정보 토글</a>
    </div>
    <div class="control">
        <a href="javascript:void(0)" class="button" onclick="toggleRoaview()" title="일반지도2">로드뷰 토글</a>
    </div>
</div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7da8ea6902bbfbf48791d0c992e45e0c"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    var trafficLayerAdded = false;
    var roadviewLayerAdded = false;

    // 지도에 로드뷰 정보가 있는 도로를 표시하도록 지도타입을 추가합니다
    function toggleTraffic() {
        if (trafficLayerAdded) {
            map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
            trafficLayerAdded = false;
        } else {
            map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
            trafficLayerAdded = true;
        }
    }

    function toggleRoaview() {
        if (roadviewLayerAdded) {
            map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
            roadviewLayerAdded = false;
        } else {
            map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
            roadviewLayerAdded = true;
        }
    }



    spots.forEach(function(spot) {
        var markerPosition  = new kakao.maps.LatLng(spot.latitude, spot.longitude);
        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="padding:5px;">' + spot.name + '</div>' // 인포윈도우에 표시될 내용
        });

        marker.setMap(map); // 마커를 지도에 표시
        kakao.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map, marker); // 마커 클릭시 인포윈도우 표시
        });
    });




    // 아래 코드는 위에서 추가한 로드뷰 도로 정보 지도타입을 제거합니다
    // map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
</script>
</body>
</html>