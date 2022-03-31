<%@ page import="org.springframework.security.core.context.SecurityContext" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.min.project.config.auth.MyUserDetails" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="kr" xmlns:th="http://www.thymeleaf.org">

<head>
    <!-- CSS -->
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/sketchy/bootstrap.min.css"
          integrity="sha384-RxqHG2ilm4r6aFRpGmBbGTjsqwfqHOKy1ArsMhHusnRO47jcGqpIQqlQK/kmGy9R" crossorigin="anonymous">
    <!-- 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

    <!-- 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<h1>
    회원가입
</h1>
<%--<form action="/auth/register1" method="post">--%>
<%--    <td>--%>
<%--        <input id="email" type="text" name="email">--%>
<%--        email--%>
<%--    </td>--%>
<%--    <td>--%>
<%--        <input id="username" type="text" name="username">--%>
<%--        name--%>
<%--    </td>--%>
<%--    <td>--%>
<%--        <input id="password" type="text" name="password">--%>
<%--        password--%>
<%--    </td>--%>

<%--    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>--%>
<%--</form>--%>
<form action="/auth/register1" method="post">
    <div class="form-group">
        <label class="col-form-label mt-4" for="inputDefault">이메일</label>
        <input style="width:300px;height:50px;font-size:30px;" type="email" id="email" name="email" class="form-control" placeholder="이메일">
    </div>
    <div class="form-group">
        <label class="col-form-label mt-4" for="inputDefault">이름</label>
        <input style="width:300px;height:50px;font-size:30px;" type="text" id="username" name="username" class="form-control" placeholder="이름">
    </div>
    <div class="form-group">
        <label class="col-form-label mt-4" for="inputDefault">비밀번호</label>
        <input style="width:300px;height:50px;font-size:30px;" type="text" id="password" name="password" class="form-control" placeholder="비밀번호"
               id="inputDefault">
    </div>
    <button type="submit" class="btn btn-primary">회원가입</button>
</form>

</body>


</html>