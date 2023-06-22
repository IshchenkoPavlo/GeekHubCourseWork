<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Purchase invoice</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1>Purchase invoice</h1>
<form name="purchase-invoice" action="purchase-invoice-add" method="post" id="purchase-invoice">
    <div class="row mx-2">
        <div class="col-3">
            <label for="docDate" class="form-label">Name : </label>
            <input type="date" value="${currentDay}" class="form-control" name="docDate" id="docDate"/><br/>
        </div>
        <div class="col-9">
            <label for="comment" class="form-label">Comment: </label>
            <input type="text" class="form-control" name="comment" id="comment"/>
        </div>
    </div>
    <div class="row mt-2 mx-2" id="content">
        <table class="table table-striped table-hover" id="goodsTable">
            <thead class="thead-dark">
            <tr>
                <th>Goods</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Amount</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
    <input type="button" class="btn btn-primary" value="Add row" id="add-row-btn"/>
    <input type="submit" class="btn btn-primary" value="Submit" id="submit-btn"/>
</form>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="js/purchase-invoice.js"></script>
<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>

