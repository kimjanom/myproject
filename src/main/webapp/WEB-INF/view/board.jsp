<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="kr" xmlns:th="http://www.thymeleaf.org">

<head>
    <!-- CSS -->
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/sketchy/bootstrap.min.css" integrity="sha384-RxqHG2ilm4r6aFRpGmBbGTjsqwfqHOKy1ArsMhHusnRO47jcGqpIQqlQK/kmGy9R" crossorigin="anonymous">
    <!-- 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

    <!-- 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <meta charset="UTF-8">
    <title>게시판 - 글쓰기</title>
    <link rel='stylesheet' href='/webjars/bootstrap/4.5.0/css/bootstrap.min.css'>

<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>게시판 목록</title>
<%--    <style>--%>
<%--        table {--%>
<%--            width: 100%;--%>
<%--            border: 1px solid #444444;--%>
<%--            border-collapse: collapse;--%>
<%--        }--%>
<%--        table th {--%>
<%--            border: 1px solid #444444;--%>
<%--            text-align: center;--%>
<%--            height: 40px;--%>
<%--            background-color: dodgerblue;--%>
<%--            color: cornsilk;--%>
<%--        }--%>
<%--        table td {--%>
<%--            border: 1px solid #444444;--%>
<%--            text-align: center;--%>
<%--        }--%>
<%--    </style>--%>
</head>
<body>
<%@include file="header.jsp"%>
<form action="/boardSearch" method="get" class="d-flex">
    <input name="search" class="form-control me-sm-2" type="text" placeholder="Search">
    <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
</form>

<div style="text-align: center;">
    <h1>게시판 목록</h1>
    <table class="table table-hover">
        <tr>
            <th scope="col">id</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">등록일</th>
        </tr>
        <c:forEach var="board" items="${upLoadList}">
            <tr class="table-dark">

                <th scope="row">
                        ${board.id}
                </th>
                <td>
                    <a class="table-dark" href="<c:url value="/mp3Player/+${board.id}"/>" >${board.title}</a>
<%--                    <a href="/mp3Player/">${board.title}</a>--%>
                </td>
                <td>
                    ${board.name}
                </td>
                <td>
                    <fmt:parseDate value="${board.uploadDate}"
                        pattern="yyyy-MM-dd" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${parsedDateTime}"/>
<%--                    ${board.uploadDate}--%>
                </td>


            </tr>
        </c:forEach>
<%--        <tr th:each="board :${upLoadList}">--%>
<%--            <td>${board}</td>&ndash;%&gt;--%>
<%--        </tr>--%>

    </table>

</div>
</body>
</html>