let pressedBtnForChoseGoods;

$(function () {
    $(document).on("click", "#myDiv", function() {
        choseGoods(this);
    });

    $(document).on("change", ".change-quantity-price", function() {
        onChangeQuantityPrice(this);
    });

    $("#add-row-btn").on("click", function (event) {
        event.preventDefault();
        addRow(this);
    });

    $("#purchase-invoice").on("submit", function (event) {
        event.preventDefault();
        sendForm(this);
    });
});

function choseGoods(btn) {
    const newWindow = window.open("goods?forchose=true", "_blank");

    pressedBtnForChoseGoods = btn;
}

function afterChoseArticle(goodsId) {
    const currentRow = pressedBtnForChoseGoods.parentNode.parentNode.parentNode;

    $.ajax({
        url: '/api/v1/article' + '?id=' + goodsId,
        method: 'GET',
        success: function (goods) {
            $(currentRow).data("goodsId", goodsId);
            currentRow.querySelector('[name="goods"]').value = goods.name;
            currentRow.querySelector('[name="price"]').value = goods.pricePurchase;
        }
    });
}

function onChangeQuantityPrice(elem) {
    const currentRow = elem.parentNode.parentNode;
    let quantity = currentRow.querySelector('[name="quantity"]').value;
    let price = currentRow.querySelector('[name="price"]').value;

    let amount = "";
    if (quantity != "" && price != "") {
        amount = parseFloat(quantity) * parseFloat(price);
    }

    currentRow.querySelector('[name="amount"]').value = amount;
}

class Doc {
    constructor(docDate, comment, rows) {
        this.docDate = docDate;
        this.comment = comment;
        this.rows = [];
    }
}

class DocRow {
    constructor(rowNum, goodsId, quantity, price, amount) {
        this.rowNum = rowNum;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }
}

function sendForm(frm) {
    const docDate = frm.elements.docDate.value
    const comment = frm.elements.comment.value;
    const doc = new Doc(docDate, comment);

    const table = document.getElementById("goodsTable");
    const rows = table.rows;
    for (let i = 1; i < rows.length; i++) {
        let row = rows[i];
        const goodsId = $(row).data("goodsId");
        const quantity = row.querySelector('[name="quantity"]').value;
        const price = row.querySelector('[name="price"]').value;
        const amount = row.querySelector('[name="amount"]').value;

        doc.rows.push(new DocRow(i, goodsId, quantity, price, amount));
    }

    const jsonString = JSON.stringify(doc);

    $.ajax({
        url: "/api/v1/purchase-invoice",
        type: "POST",
        data: jsonString,
        contentType: "application/json; charset=utf-8",
//        dataType: "json",
        success: function (response) {
            console.log("Document sent", response);
            window.close();
        },
        error: function (error) {
            console.error("Submit error", error);
            window.open("error-page?errorText=" + encodeURIComponent(error.responseText), "_blank");
        }
    });
};

function addRow(btn) {
    const table = $('#goodsTable');

    newRowHtml =`
            <tr data-goodsId="">
                <td>
                    <div class="input-group">
                        <input type="text" class="form-control" name="goods" value=""/>
                        <div class="input-group-addon border" id="myDiv"> . . . </div>
                    </div>
                </td>
                <td><input type="number" step="0.001" class="form-control change-quantity-price" name="quantity" value="0"/></td>
                <td><input type="number" step="0.01" class="form-control change-quantity-price" name="price" value="0"/></td>
                <td><input type="number" step="0.01" class="form-control" name="amount" value="0"/></td>
            </tr>
                `;

    table.append(newRowHtml);
};

addRow(null);

window.addEventListener("message",
    function (event) {
        afterChoseArticle(event.data);
    });
