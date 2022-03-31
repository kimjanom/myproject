<%@ page import="org.springframework.ui.Model" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <title>
        HTML audio autoplay Attribute
    </title>
    <!-- CSS -->
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/sketchy/bootstrap.min.css"
          integrity="sha384-RxqHG2ilm4r6aFRpGmBbGTjsqwfqHOKy1ArsMhHusnRO47jcGqpIQqlQK/kmGy9R" crossorigin="anonymous">
    <%--    <!-- 테마 -->--%>
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">--%>

    <!-- 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>--%>

    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>--%>

    <%--    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>--%>
    <%--    &lt;%&ndash;                   -----------------------  &ndash;%&gt;--%>
    <style>
        .list-group{
            text-align: center;
        }
    </style>
</head>
<body>
<%@include file="header.jsp" %>


<div class="card bg-light mb-3" style="max-width: 20rem;">
    <div class="list-group" >
        <a  href="/loginview" class="list-group-item list-group-item-action">로그인</a>
    </div>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="register">회원가입</a></li>
        <li class="breadcrumb-item"><a href="#">비밀번호 찾기</a></li>
    </ol>
</div>

</body>

<h1>Welcome to ADMIN</h1>

</table>
