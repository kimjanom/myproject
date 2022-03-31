<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>게시판 - 글쓰기</title>
    <link rel='stylesheet' href='/webjars/bootstrap/4.5.0/css/bootstrap.min.css'>
</head>
<body>
<header th:insert="common/header.html"></header>
<div class="container">
    <form action="/update/+${id}" method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label for="inputTitle" class="col-sm-2 col-form-label"><strong>title</strong></label>
            <div class="col-sm-10">
                <input type="text" name="title" class="form-control" id="inputTitle">
            </div>
        </div>

        <div class="form-group row">
            <label for="inputContent" class="col-sm-2 col-form-label"><strong>content</strong></label>
            <div class="col-sm-10">
                <textarea type="text" name="content" class="form-control" id="inputContent"></textarea>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputFile" class="col-sm-2 col-form-label"><strong>file</strong></label>
            <div class="col-sm-10">
                <div class="custom-file" id="inputFile">
                    <input name="file" type="file" class="custom-file-input" id="customFile">
                    <label class="custom-file-label" for="customFile">.</label>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-auto mr-auto"></div>
            <div class="col-auto">
                <input class="btn btn-primary" type="submit" role="button" value="update">
            </div>
        </div>
    </form>
</div>
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