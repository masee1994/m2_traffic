<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>지도에 로드뷰 도로 표시하기</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7da8ea6902bbfbf48791d0c992e45e0c"></script>
    <style>
        #map { position: relative; width: 100%; height: 600px; }
        .control { position: absolute; top: 10px; right: 10px; z-index: 5; }
        .button { padding: 5px 10px; background-color: white; border: 1px solid #e3e6f0; cursor: pointer; border-radius: 6px; }
        .button:hover { background-color: #f8f8f8; }
        .custom-overlay { background: black; color: white; padding: 5px; border-radius: 3px; }
    </style>
</head>
<body>

<div id="map">
    <div class="control" style="right: 120px;">
        <a href="javascript:void(0)" class="button" onclick="toggleTraffic()" title="일반지도1">교통정보 토글</a>
    </div>
    <div class="control">
        <a href="javascript:void(0)" class="button" onclick="toggleRoaview()" title="일반지도2">로드뷰 토글</a>
    </div>
</div>

<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    var trafficLayerAdded = false;
    var roadviewLayerAdded = false;

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

    function fetchData() {
        $.ajax({
            url: 'bustest',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                var existingMarkers = {}; // 마커 위치를 저장할 객체

                data.forEach(function(dto) {
                    if (dto.dPx_longitude && dto.dPy_latitude && dto.name) {
                        var markerPosition = new kakao.maps.LatLng(dto.dPy_latitude, dto.dPx_longitude);
                        var markerKey = markerPosition.toString();

                        // 중복된 마커 위치인지 확인
                        if (!existingMarkers[markerKey]) {
                            existingMarkers[markerKey] = true;

                            var markerImageUrl = "resources/img/busicon.png";
                            var markerImageSize = new kakao.maps.Size(20, 20); // 마커 이미지 크기
                            var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize);

                            var marker = new kakao.maps.Marker({
                                position: markerPosition,
                                image: markerImage
                            });

                            var infowindowContent = '<div class="custom-overlay">' + dto.name + '</div>';
                            var infowindow = new kakao.maps.InfoWindow({
                                content: infowindowContent // 인포윈도우에 표시될 내용
                            });

                            marker.setMap(map); // 마커를 지도에 표시

                            // 마커에 마우스를 올렸을 때
                            kakao.maps.event.addListener(marker, 'mouseover', function () {
                                infowindow.close();
                                infowindow.open(map, marker);
                            });

                            // 마커에서 마우스를 뗐을 때
                            kakao.maps.event.addListener(marker, 'mouseout', function () {
                                infowindow.close();
                            });
                        }
                    }
                });
            },
            error: function (error) {
                console.error('Error fetching data', error);
            }
        });
    }

    $(document).ready(function () {
        fetchData(); // 페이지 로드 시 데이터 가져오기
    });
</script>

</body>
</html>