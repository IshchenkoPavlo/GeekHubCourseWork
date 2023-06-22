let openedGroupCount = 0;
let forChose = false;

$(function () {
    $("#goodsTable tbody").on("click", "tr", function () {
        isGroup = $(this).data("isgroup");
        if (isGroup) {
            openGroup(this);
        } else {
            id = $(this).data("id");
            window.opener.postMessage(id, "*");
            window.close();
        }
    });
});

function loadGoods(parentId) {
    $.ajax({
        url: '/api/v1/goods' + '?parentId=' + parentId,
        method: 'GET',
        success: function (goods) {
            const $tb = $('#goodsTable tbody')
            var html = $tb.html();
            goods.forEach((g) => {
                html = html + `<tr data-id="${g.id}"  data-isGroup="${g.isGroup}">`;
                if (g.isGroup)
                    html = html + '<td><img src="/img/Group.png"></td>';
                else
                    html = html + '<td><img src="/img/Elem.png"></td>';
                html = html + `            
                        <td>${g.id}</td>
                        <td>${g.name}</td>
                        <td>${g.price}</td>
                        <td>${g.pricePurchase}</td>
                        <td>${g.remain}</td>
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

function openGroup(tableRow) {
    let groupId = 0;
    let $table = $('#goodsTable');

    if (tableRow.rowIndex <= openedGroupCount) {
        openedGroupCount = tableRow.rowIndex - 1;

        if (openedGroupCount == 0) {
            $("#goodsTable tbody").empty();
            loadGoods(0);
            return;
        }

        let $tableRowBefor = $table.find('tr').eq(openedGroupCount)
        groupId = $tableRowBefor.data("id");
    } else {
        groupId = $(tableRow).data("id");

        let td = tableRow.querySelector('td');
        $(td).find('img').attr('src', '/img/GroupOpened.png');

        let $tableRowAfter = $table.find('tr').eq(openedGroupCount + 1)
        $(tableRow).insertBefore($tableRowAfter);

        openedGroupCount++;
    }

    $('#goodsTable tr').slice(openedGroupCount + 1).remove();
    loadGoods(groupId);
}

function getForChose() {
    return window.location.href.includes("?forchose=true");
}

forChose = getForChose();
loadGoods(0);
