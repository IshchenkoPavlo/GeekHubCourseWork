$(function () {
    $("#usersTable tbody").on("click", "tr td:not(:last-child)", function () {
        //const id = $(this).data("id");
        const id = this.parentNode.attributes['data-id'].value;
        window.open("/user-edit/" + id);
    });
});

function loadUsers() {
    $.ajax({
        url: '/api/v1/users',
        method: 'GET',
        success: function (users) {
            const $tb = $('#usersTable tbody')
            var html = "";//$tb.html();
            users.forEach((user) => {
                html = html + `<tr data-id="${user.id}">`;
                html = html + `            
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.role}</td>
                        <td>
                          <form>
                            <button onclick="deleteUser(${user.id})">Delete</button>
                          </form>
                        </td>
                                               
                    </tr>
                `;
            });
            $tb.html(html);
        },
        error: function (error) {
            console.error("loadGoods(" + parentId + ")", error);
            window.open("error?errorText=" + encodeURIComponent(error.responseText), "_blank");
        }
    });
}

function deleteUser(userId) {
    console.log(userId);
    fetch('/users/' + userId, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete item');
            } else {
                loadUsers();
            }
        })
        .catch(error => {
            console.error(error);
        });
}

loadUsers();

window.addEventListener("message",
    function (event) {
        if (event.data == "refresh") {
            loadUsers();
        }
    });
