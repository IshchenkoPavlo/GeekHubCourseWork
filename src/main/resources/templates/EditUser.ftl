<!doctype html>

<head xmlns="http://www.w3.org/1999/html">
    <title>Edit user</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>

<h1>${pageCaption}</h1>

<form name="user-edit" action="/user-edit" method="post" id="user-edit">
    <div class="row gx-1">
        <div class="row gx-5 gy-1">
            <div class="col-2">
                <label for="id" class="form-label">User id (read only): </label>
                <input type="text" value="${user.id}" class="form-control" name="id" id="id" readonly/>
            </div>
            <div class="col-4">
                <label for="name" class="form-label">User name : </label>
                <input type="text" value="${user.name}" class="form-control" name="name" id="name"/>
            </div>
        </div>
        <div class="row gx-5 gy-1">
            <div class="col-3">
                <label for="password" class="form-label">Password : </label>
                <input type="password" value="" class="form-control" name="password" id="password"/>
            </div>
            <div class="col-3">
                <label for="role" class="form-label">Role : </label>
                <select name="role" class="form-select" id="role">
                    <#list roles as role>
                        <#if (role == user.role)>
                            <option value="${role}" selected>${role}</option>
                        <#else>
                            <option value="${role}">${role}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>
    </div>
</form>

<div class="row g-2">
    <div class="row gx-5  gy-3">
        <div class="col-6 gx-6">
            <button onclick="submitForm()">${buttonCaption}</button>
        </div>
    </div>
</div>

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
<script>
    function submitForm() {
        const form = $('#user-edit');
        const data = form.serializeArray().reduce(function (obj, item) {
            obj[item.name] = item.value;
            return obj;
        }, {});
        $.ajax({
            url: "/api/v1/users",
            type: "${metodName}",
//            type: "post",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                console.log(response);
                window.opener.postMessage("refresh", "*");
                window.close();
            },
            error: function (error) {
                console.log(error);
                window.open("/error-page?errorText=" + encodeURIComponent(error.responseText), "_blank");
            }
        });
    }
</script>

