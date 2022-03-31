<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <title>
        HTML audio autoplay Attribute
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
</head>

<body style="text-align: center">

<h1 style="color: green">
    GeeksforGeeks
</h1>

<h2>HTML audio autoplay Attribute</h2>

<audio controls autoplay>

    <source var="upLoadList" items="${upLoadList}" src="http://localhost:8080/audioLink/+${id}.mp3" type="audio/mpeg">
</audio>

<%--                                            --%>

<table class="table table-striped table-hover">

    <tr>
        <td id="title">작성일</td>
        <td>${date}</td>
    </tr>
    <tr>
        <td id="title">작성자</td>
        <td>${name}</td>
    </tr>
    <tr>
        <td id="title">
            제 목
        </td>
        <td>
            ${title}
        </td>
    </tr>
    <tr>
        <td id="title">
            내 용
        </td>
        <td>
            ${content}
        </td>
    </tr>





    <tr align="center" valign="middle">
        <td colspan="5">
            <div class="container">
                <div class="row">
                    <div class="col-3">
                        <form action="/Download/+${id}" method="post">
                            <input type="submit" value="다운">
                        </form>
                    </div>
                    <c:if test="${email eq session}">
                        <div class="col-3">
                            <form action="/updateview/+${id}" method="post">
                                <input type="submit" value="수정">
                            </form>
                        </div>
                        <div class="col-3">
                            <form action="/delete/+${id}" method="post">
                                <input type="submit" value="삭제">
                            </form>
                        </div>
                    </c:if>
                    <div class="col-3">
                        <input type="button" value="목록"
                               onclick="javascript:location.href='/board/'">
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>

</body>

</html>