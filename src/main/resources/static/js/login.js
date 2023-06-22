class User {
    constructor(name, password) {
        this.name = name;
        this.password = password;
    }
}

function registration() {
    const name = document.querySelector('[name="username"]').value
    const password = document.querySelector('[name="password"]').value;
    const user = new User(name, password);

    const jsonString = JSON.stringify(user);

    $.ajax({
        url: "/api/v1/user-registration",
        type: "POST",
        data: jsonString,
        contentType: "application/json; charset=utf-8",
//        dataType: "json",
        success: function (response) {
            //console.log("Document sent", response);
            const frm = document.getElementById("login");
            frm.submit();
        },
        error: function (error) {
            console.error("Submit error", error);
            window.open("error-page?errorText=" + encodeURIComponent(error.responseText), "_blank");
        }
    });
}
