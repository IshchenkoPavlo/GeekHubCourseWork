<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Store: main page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <#--        <a class="navbar-brand" href="#">Панель навигации</a>-->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Переключатель навигации">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Main</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/goods" target="_blank">Goods list</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/purchase-invoice" target="_blank">Purchase invoice</a>
                    </li>
                </#if>
                <li class="nav-item">
                    <a class="nav-link" href="/sales-invoice" target="_blank">Sales invoice</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/upload-goods" target="_blank">Upload goods</a>
                    </li>
                </#if>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Reports
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/report-goods-remains" target="_blank">Goods remains</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="/report-sales" target="_blank">Sales for period</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="/report-movements-with-remains" target="_blank">Movements
                                with remains</a></li>
                    </ul>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin-panel" target="_blank">Admin panel</a>
                    </li>
                </#if>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script src="js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>

