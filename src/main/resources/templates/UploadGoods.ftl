<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Store: Upload goods</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1>Upload goods list</h1>
<form name="uploader" id="goods-load-csv" enctype="multipart/form-data" method="POST">
    <input name="userfile" type="file" />
    <button type="submit" name="submit">Загрузить</button>
</form>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
<script src="js/upload-goods.js"></script>

