<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title>Hello JSP</title>
</head>
<body class="container">
<div class="jumbotron">
    <h1>Form</h1>
    <p>로그인 화면.</p>
</div>


<!-- 로그인 실패 시 경고: https://getbootstrap.com/docs/4.1/components/alerts/#dismissing -->
<%
    System.out.println("로그인 실패시:"+request.getAttribute("fail"));
    if (request.getAttribute("fail") != null) { %>
<div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong>로그인 에러!</strong> 이메일 또는 비밀번호가 일치하지 않습니다.
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<% } %>
<!-- 로그인 폼: https://getbootstrap.com/docs/4.1/components/forms/ -->
<form action="/authenticate" method="post">
    <div class="form-group">
        <label>이메일</label>
        <input name="email" type="email" class="form-control" />

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </div>
    <div class="form-group">
        <label>비밀번호</label>
        <input name="password" type="password" class="form-control" />
    </div>
    <button type="submit" class="btn btn-primary">전송</button>
</form>
<form action="/register" method="get">
<button type="submit"  class="btn btn-primary">회원가입</button>
</form>
<!-- Optional JavaScript -->
<Script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<Script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
