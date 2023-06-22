<!doctype html>

<head xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
    <title>Store: login page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<body>

<#if error != "">
    <div>
        ${error}
    </div>
</#if>

<div class="d-flex align-items-center vh-100">
    <form class="mx-auto my-auto" action="/login" method="post" name="login" id="login">
        <div class="row mx-2">
            <label for="username" class="form-label"> User Name : </label>
            <input type="text" class="form-control" name="username"/>

        </div>
        <div class="row mx-2">
            <label for="password" class="form-label"> Password:</label>
            <input type="password" class="form-control" name="password"/>
        </div>
        <br>
        <div class="row mx-2 gy-3">
            <input type="submit" class="btn btn-primary" value="Sign In"/>
        </div>
        <br>
        <div class="row mx-2 gy-3">
            <button type="button" onclick="registration()" value="Register" id="register"/> Registration </button>
        </div>
    </form>
</div>
</body>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/js/login.js"></script>

</html>

