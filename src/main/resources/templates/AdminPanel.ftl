<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Store: Users list</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1 class="title">Admin panel</h1>
<div class="row mt-2 mx-2" id="content">
    <table class="table table-striped table-hover" id="usersTable">
        <thead class="thead-dark">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Role</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<div class="row g-2">
    <div class="row gx-5  gy-3">
        <div class="col-6 gx-6">
            <button onclick="window.open('/user-add' , '_blank')" class="btn btn-primary">Add user</button>
        </div>
    </div>
</div>


<script src="/js/jquery-3.6.0.min.js"></script>
<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
<script src="js/users.js"></script>

