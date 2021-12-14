<%--
  Created by IntelliJ IDEA.
  User: mullaak47
  Date: 08.06.2020
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body class="bg-warning">
<h1 class="text-center">LOGOWANIE</h1>
<img src="/img/forte.png" alt="" class="mx-auto d-block"><br>
<div class="container text-center">
    <form method="post">
        <div class="text-center"><label>Nazwa użytkownika:<input type="text" name="username"/></label></div>
        <div class="text-center"><label>Hasło:<input type="password" name="password"/></label></div>
        <div class="text-center"><input type="submit" value="Zaloguj"/></div>
        <input class="text-center" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<div class="text-center"><a href="/users/addUser" class="btn btn-light">Rejestracja</a></div>
</body>
</html>
