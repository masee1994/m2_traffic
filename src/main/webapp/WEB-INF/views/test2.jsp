<%--
  Created by IntelliJ IDEA.
  User: hanseulma
  Date: 5/14/24
  Time: 2:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Accident Information</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .tab-container {
            display: flex;
            border-bottom: 2px solid #ddd;
            margin-bottom: 0; /* margin-bottom 제거 */
        }
        .tab {
            padding: 10px 20px;
            cursor: pointer;
            border: 1px solid #ddd;
            border-bottom: none;
            background-color: #f8f9fc;
            margin-right: 5px;
            font-weight: lighter;
            color: #6c757d;
        }
        .tab.active {
            background-color: white;
            font-weight: bold;
            border-top: 2px solid #007bff;
            border-left: 2px solid #ddd;
            border-right: 2px solid #ddd;
            border-bottom: none; /* border-bottom 변경 */
            color: #007bff;
        }
        .tab-content {
            display: none;
            max-height: 400px; /* 원하는 높이 설정 */
            overflow-y: auto; /* 스크롤바 추가 */
            border: 1px solid #ddd; /* Border to match tab border */
            border-top: none; /* Remove top border to connect with tabs */
            padding: 10px;
            background-color: white; /* Background color to match tabs */
        }
        .tab-content.active {
            display: block;
        }
        .icon-box img {
            width: 20px;
            height: 20px;
        }
        .acc-txt {
            display: flex;
            flex-direction: column;
        }
        .list-group-item {
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            background-color: #f8f9fc;
            border: 1px solid #e3e6f0;
        }
        .list-group-item-heading {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .date small {
            color: #6c757d;
        }
    </style>
</head>
<body>
<div class="tab-container">
    <div class="tab active" data-tab="all">전체</div>
    <div class="tab" data-tab="A01">사고/고장</div>
    <div class="tab" data-tab="A02">공사</div>
    <div class="tab" data-tab="A03">기상/화재</div>
    <div class="tab" data-tab="A04">집회/행사</div>
</div>

<div id="accInfoContainer">
    <div class="tab-content active" id="all"><p>Loading Traffic data...</p></div>
    <div class="tab-content" id="A01"><p>Loading Traffic data...</p></div>
    <div class="tab-content" id="A02"><p>Loading Traffic data...</p></div>
    <div class="tab-content" id="A03"><p>Loading Traffic data...</p></div>
    <div class="tab-content" id="A04"><p>Loading Traffic data...</p></div>
</div>

<script>
    $(document).ready(function () {
        function initializeTabs() {
            $('.tab').click(function () {
                var tabId = $(this).data('tab');

                $('.tab').removeClass('active');
                $(this).addClass('active');

                $('.tab-content').removeClass('active');
                $('#' + tabId).addClass('active');
            });
        }

        function loadAccInfo(data) {
            var allContent = '';
            var a01Content = '';
            var a02Content = '';
            var a03Content = '';
            var a04Content = '';

            data.forEach(function (dto) {
                var content = '<div style="border-bottom: 1px solid #e3e6f0;">' +
                    '<a href="#" class="list-group-item w-list-group-item" style="border: none;">' +
                    '<div class="acc-txt">' +
                    '<h5 class="list-group-item-heading">' +
                    '<span class="icon-box"><img src="https://topis.seoul.go.kr/images/map/' + dto.acc_type + '.png" alt="' + dto.acc_type + '"></span>' +
                    dto.acc_info + '&nbsp;';

                if (dto.acc_road_code === '010') {
                    content += '<small class="label label-warning" style="color: white; background-color: #A46A19; border-radius: 3px;">&nbsp;&nbsp;부분통제&nbsp;&nbsp;</small>';
                } else if (dto.acc_road_code === '009') {
                    content += '<small class="label label-warning" style="color: white; background-color: #FF1111; border-radius: 3px;">&nbsp;&nbsp;전면통제&nbsp;&nbsp;</small>';
                }

                content += '</h5><div class="date"><small>발생일시 ' + dto.occr_date + ' &nbsp; ' + dto.occr_time + '<br> 완료예정 ' + dto.exp_clr_date + ' &nbsp; ' + dto.exp_clr_time + '</small></div></div></a></div>';

                allContent += content;

                if (dto.acc_type === 'A01' || dto.acc_type === 'A02' || dto.acc_type === 'A03' || dto.acc_type === 'A05' || dto.acc_type === 'A06' || dto.acc_type === 'A07') {
                    a01Content += content;
                } else if (dto.acc_type === 'A04') {
                    a02Content += content;
                } else if (dto.acc_type === 'A08' || dto.acc_type === 'A09') {
                    a03Content += content;
                } else if (dto.acc_type === 'A10') {
                    a04Content += content;
                }
            });

            $('#all').html(allContent);
            $('#A01').html(a01Content);
            $('#A02').html(a02Content);
            $('#A03').html(a03Content);
            $('#A04').html(a04Content);
        }

        if (!window.ajaxCalled) {
            window.ajaxCalled = true;
            // AJAX 요청을 통해 test2 데이터를 가져옴
            $.ajax({
                url: 'test2',
                method: 'GET',
                success: function (data) {
                    loadAccInfo(data);
                    initializeTabs(); // AJAX 요청 후 탭 클릭 이벤트 리스너 다시 설정

                    // 스크롤 추가
                    $('.tab-content').css({
                        'max-height': '400px',
                        'overflow-y': 'auto'
                    });
                },
                error: function (error) {
                    console.error('Error fetching data', error);
                    $('.tab-content').html('<p>Error fetching traffic data.</p>');
                }
            });
        } else {
            initializeTabs(); // 처음 로드 시 탭 클릭 이벤트 리스너 설정

            // 스크롤 추가
            $('.tab-content').css({
                'max-height': '400px',
                'overflow-y': 'auto'
            });
        }
    });
</script>

</body>
</html>

