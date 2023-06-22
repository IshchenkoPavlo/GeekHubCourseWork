<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Report Movments with remains</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1>Movments with remains</h1>
<form name="report-movements-with-remains" action="/report-movements-with-remains/show" method="get" id="report-sales">
    <div class="row mx-2">
        <div class="col-3">
            <label for="remainsDate" class="form-label">Date of remains : </label>
            <input type="date" value="${currentDay}" class="form-control" name="dateFrom" id="dateFrom"/><br/>
        </div>
        <div class="col-3">
            <label for="remainsDate" class="form-label">to : </label>
            <input type="date" value="${currentDay}" class="form-control" name="dateTo" id="dateTo"/><br/>
        </div>
        <div class="input-group">
            <label for="goodsName" class="form-label">Goods : </label>
            <input type="text" class="form-control" name="goodsName" id="goodsName" value=""/>
            <div class="input-group-addon border" id="myDiv"> . . . </div>
        </div>
        <input type="text"  style="display: none;" class="form-control" name="goodsId" id="goodsId" value="" />
    </div>
    <input type="submit" class="btn btn-primary" value="Get report" id="submit-btn"/>
</form>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
<script src="/js/report-movements-with-remaind.js"></script>

