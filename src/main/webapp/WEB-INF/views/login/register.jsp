<%--
  Created by IntelliJ IDEA.
  User: mullaak47
  Date: 21.06.2020
  Time: 10:03
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
<h1 class="text-center">REJESTRACJA</h1>
<img src="/img/forte.png" alt="" class="mx-auto d-block"><br>
<section class="log">
    <div class="container text-center">
        <%--@elvariable id="user" type=""--%>
        <form:form method="post" modelAttribute="user">
            <div class="form-group">
                <label for="userId">Nazwa użytkownika:</label>
                <form:input type="text" path="username" id="userId"/>
                <form:errors path="username"/>
<%--                <input type="text" class="form-control" id="userId" placeholder="Podaj nazwę użytkownika" name="uname" required>--%>
<%--                <div class="valid-feedback">Poprawanie.</div>--%>
<%--                <div class="invalid-feedback">Proszę wypełnić pole.</div>--%>
            </div>
            <div class="form-group">
                <label for="password" class="control-label">Hasło</label>
                <form:input type="password" path="password" id="passwordId"/>
                <form:errors path="password"/>
<%--                <input type="password" class="form-control" id="inputPassword" placeholder="Hasło" required name="ha">--%>
<%--                <div class="valid-feedback">Poprawanie.</div>--%>
            </div>
<%--            <div><br></div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="password" class="control-label">Powtórz hasło</label>--%>
<%--&lt;%&ndash;                <form:input type="password" path="password" id="passwordId" placeholder="Powtórzenie hasła" name="ha2"/><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <form:errors path="password"/><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <input type="password" class="form-control" id="inputPasswordConfirm" placeholder="Powtórzenie hasła" required name="ha2">&ndash;%&gt;--%>
<%--                <div class="valid-feedback">Poprawanie.</div>--%>
<%--                <div class="help-block with-errors"></div>--%>
<%--            </div>--%>
<%--            <button type="submit" class="btn btn-light">Rejestruj</button>--%>
            <input type="submit" value="Dodaj">
        </form:form>
    </div>
</section>

</body>
</html>
