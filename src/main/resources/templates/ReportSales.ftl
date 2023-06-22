<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Report sales</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1>Report sales</h1>
<form name="report-sales" action="/report-sales/show" method="get" id="report-sales">
    <div class="row mx-2">
        <div class="col-3">
            <label for="remainsDate" class="form-label">Date of remains : </label>
            <input type="date" value="${currentDay}" class="form-control" name="dateFrom" id="dateFrom"/><br/>
        </div>
        <div class="col-3">
            <label for="remainsDate" class="form-label">to : </label>
            <input type="date" value="${currentDay}" class="form-control" name="dateTo" id="dateTo"/><br/>
        </div>
    </div>
    <input type="submit" class="btn btn-primary" value="Get report" id="submit-btn"/>
</form>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>

