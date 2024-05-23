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
            url: 'test',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                data.forEach(function(dto) {
                    if(dto.dPx_longitude && dto.dPy_latitude) {
                        var markerPosition = new kakao.maps.LatLng(dto.dPy_latitude, dto.dPx_longitude);
                        var markerImageUrl = getMarkerImageUrl(dto.acc_type); // acc_type 값을 사용하여 이미지 URL 가져오기
                        var markerImageSize = new kakao.maps.Size(30, 30); // 마커 이미지 크기
                        var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize);

                        var marker = new kakao.maps.Marker({
                            position: markerPosition,
                            image: markerImage
                        });

                        var infowindow = new kakao.maps.InfoWindow({
                            content: '<div style="padding:5px;">' + dto.acc_info +'</div>' // 인포윈도우에 표시될 내용
                        });

                        marker.setMap(map); // 마커를 지도에 표시

                        var isInfowindowOpen = false; // 인포윈도우가 열려있는지 상태를 저장하는 변수
                        kakao.maps.event.addListener(marker, 'click', function() {
                            if (isInfowindowOpen) {
                                infowindow.close();
                            } else {
                                infowindow.open(map, marker);
                            }
                            isInfowindowOpen = !isInfowindowOpen; // 상태를 반전
                        });
                    }
                });
            },
            error: function(error) {
                console.error('Error fetching data', error);
            }
        });
    }

    function getMarkerImageUrl(acc_type) {
        var baseUrl = "https://topis.seoul.go.kr/images/map/";
        switch(acc_type) {
            case 'A01':
                return baseUrl + "A01.png";
            case 'A02':
                return baseUrl + "A02.png";
            case 'A03':
                return baseUrl + "A03.png";
            case 'A04':
                return baseUrl + "A04.png";
            case 'A05':
                return baseUrl + "A05.png";
            case 'A06':
                return baseUrl + "A06.png";
            case 'A07':
                return baseUrl + "A07.png";
            case 'A08':
                return baseUrl + "A08.png";
            case 'A09':
                return baseUrl + "A09.png";
            case 'A10':
                return baseUrl + "A10.png";
            case 'A11':
                return baseUrl + "A11.png";
            case 'A12':
                return baseUrl + "A12.png";
            default:
                return baseUrl + "default.png";
        }
    }

    $(document).ready(function() {
        fetchData(); // 페이지 로드 시 데이터 가져오기
    });
</script>

</body>
</html>
