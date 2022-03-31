<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <meta charset = "utf-8">

    <title>Bootstrap 활용 페이지</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head>

<body>

<div class = "container">

    <div class = "jumbotron jumbotron-fluid">

        <h1>Onepoint 블로그</h1>

        <p>당신의 개발 능력을 키워줄 홈페이지입니다.</p>

    </div>

    <h2>Alerts</h2>

    <p>지난번 포스팅에서 알려드렸던, Bootstrap Colors와 연관이 되어있으니, 참고 바랍니다.</p>

    <div class = "alert alert-success alert-dismissible">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>성공 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-primary alert-dismissible">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>중요 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-info alert-dismissible">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>정보 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-warning">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>경고 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-danger">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>위험 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-secondary">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>덜중요 알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-dark">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-light">

        <button type="button" class = "close" data-dismiss="alert">×</button>

        <strong>알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

    <div class = "alert alert-light">

        <button id="btn-test" type="button" class = "close" data-dismiss="alert">×</button>

        <strong>알림 발생!</strong> 메시지를 보시려면 <a href="#" class = "alert-link">이것</a>을 누르세요

    </div>

</div>
<script>
    $('#btn_test').on('click',function(){
        notify('bell','Title of the message!','This is a sample message! Lorem ipsum!');
    });
</script>
</body>

</html>

​