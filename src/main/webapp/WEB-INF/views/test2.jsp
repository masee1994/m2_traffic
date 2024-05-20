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
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<script>
    $(document).ready(function () {
        if (!window.ajaxCalled) {
            window.ajaxCalled = true;
            // AJAX 요청을 통해 test2 데이터를 가져옴
            $.ajax({
                url: 'test2',
                method: 'GET',
                success: function (data) {
                    $('#accInfoContainer').html(data);
                },
                error: function (error) {
                    console.error('Error fetching data', error);
                }
            });
        }
    });
</script>
<style>
    #accInfoContainer {
        max-height: 400px; /* 원하는 높이 설정 */
        overflow-y: auto; /* 스크롤바 추가 */
    }
</style>
<body>
<div id="accInfoContainer">
    <c:forEach var="dto" items="${accInfo}">
        <div style="border-bottom: 1px solid #e3e6f0;">
            <a href="#" class="list-group-item w-list-group-item" style="border: none;">
                <div class="acc-txt">
                    <h5 class="list-group-item-heading">
                        <c:if test="${dto.acc_type == 'A01'}">
                            <span class="icon-box">
                                <img src="https://topis.seoul.go.kr/images/map/A01.png" alt="사고">
                            </span>
                        </c:if>
                        <c:if test="${dto.acc_type == 'A02'}">
                            <span class="icon-box">
                                <img src="https://topis.seoul.go.kr/images/map/A02.png" alt="고장">
                            </span>
                        </c:if>
                        <c:if test="${dto.acc_type == 'A03'}">
                            <span class="icon-box">
                                <img src="https://topis.seoul.go.kr/images/map/A03.png" alt="집회">
                            </span>
                        </c:if>
                        <c:if test="${dto.acc_type == 'A04'}">
                            <span class="icon-box">
                                <img src="https://topis.seoul.go.kr/images/map/A04.png" alt="공사">
                            </span>
                        </c:if>

                            ${dto.acc_info}&nbsp;
                        <c:if test="${dto.acc_road_code == '010'}">
                        <small
                            class="label label-warning"
                            style="color: white; background-color: #A46A19; border-radius: 3px;">&nbsp;&nbsp;부분통제&nbsp;&nbsp;</small>
                        </c:if>
                        <c:if test="${dto.acc_road_code == '009'}">
                            <small
                                    class="label label-warning"
                                    style="color: white; background-color: #FF1111; border-radius: 3px;">&nbsp;&nbsp;전면통제&nbsp;&nbsp;</small>
                        </c:if>
                    </h5>
                    <div class="date">
                        <small>발생일시 ${dto.occr_date} &nbsp; ${dto.occr_time}<br> 완료예정 ${dto.exp_clr_date}
                            &nbsp; ${dto.exp_clr_time}
                        </small>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>
</div>

</body>
</html>