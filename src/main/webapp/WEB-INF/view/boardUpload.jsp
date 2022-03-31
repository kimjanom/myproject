
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
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
</head>
<body>
<%@include file="header.jsp"%>
<%--<header th:insert="common/header.html"></header>--%>
<%--<div class="container">--%>
<%--    <form action="/upload/media" method="post" enctype="multipart/form-data">--%>
<%--        <div class="form-group row">--%>
<%--            <label for="inputTitle" class="col-sm-2 col-form-label"><strong>title</strong></label>--%>
<%--            <div class="col-sm-10">--%>
<%--                <input type="text" name="title" class="form-control" id="inputTitle">--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="form-group row">--%>
<%--            <label for="inputContent" class="col-sm-2 col-form-label"><strong>content</strong></label>--%>
<%--            <div class="col-sm-10">--%>
<%--                <textarea type="text" name="content" class="form-control" id="inputContent"></textarea>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="form-group row">--%>
<%--            <label for="inputFile" class="col-sm-2 col-form-label"><strong>file</strong></label>--%>
<%--            <div class="col-sm-10">--%>
<%--                <div class="custom-file" id="inputFile">--%>
<%--                    <input name="file" type="file" class="custom-file-input" id="customFile">--%>
<%--                    <label class="custom-file-label" for="customFile">.</label>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="row">--%>
<%--            <div class="col-auto mr-auto"></div>--%>
<%--            <div class="col-auto">--%>
<%--                <input class="btn btn-primary" type="submit" role="button" value="upload">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>
<form action="/upload/media" method="post" enctype="multipart/form-data">
    <fieldset>
        <legend>Legend</legend>

        <div class="form-group">
            <label for="exampleInputEmail1" class="form-label mt-4">title</label>
            <input type="text" name="title" class="form-control" id="exampleInputEmail1" aria-describedby="title" placeholder="title">
            <small id="emailHelp" class="form-text text-muted">제목을 적어주세요</small>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1" class="form-label mt-4">Content</label>
            <input type="text" name="content" class="form-control" id="exampleInputPassword1" placeholder="content">
        </div>
        <div class="form-group">
            <label for="formFile" class="form-label mt-4">Default file input example</label>
            <input class="form-control" type="file" id="formFile" name="file">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </fieldset>
</form>
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script>
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
</script>
</body>
</html>