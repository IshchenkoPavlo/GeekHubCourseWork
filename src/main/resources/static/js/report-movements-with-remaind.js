$(function () {
    $(document).on("click", "#myDiv", function() {
        choseGoods(this);
    });

});

function choseGoods(btn) {
    const newWindow = window.open("goods?forchose=true", "_blank");
}

function afterChoseArticle(goodsId) {
    $.ajax({
        url: '/api/v1/article' + '?id=' + goodsId,
        method: 'GET',
        success: function (goods) {
            document.querySelector('[name="goodsId"]').value = goodsId;
            document.querySelector('[name="goodsName"]').value = goods.name;
        }
    });
}

window.addEventListener("message",
    function (event) {
        afterChoseArticle(event.data);
    });
