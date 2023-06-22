$(function () {
    $("form#goods-load-csv").submit(function (event) {
        event.preventDefault();

        var formData = new FormData($(this)[0]);

        $.ajax({
            url: '/goods/upload-csv',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {
                alert("Upload OK");
            },
            error: function (xhr, status, error) {
                console.log(xhr.responseText);
                alert(xhr.responseText);
            }
        });

        return false;
    });
});
