$(function(){

    var productId = getQueryString("product");

    if(productId>0){
        initShopDetail(productId);
    }

    function initShopDetail() {
        $.getJSON("/frontend/getProduct/"+productId,function (data) {
            if(data.code === 100){
                console.log(data);
                var product = data.extend.map.product;
                var html = $("<span>"+product.productName+"<span/>");
                $(".product-title").html(html);
                html = $("<img src='"+product.imgAddress+"' " +
                    "title='"+product.productName+"' style='height:80%;width:100%;display: block''>")
                $(".product-img").html(html);
                html = $("<li>商品描述:<p>&nbsp;&nbsp;"+product.productDesc+"</p>" +
                    "<li>原价:<span>"+product.normalPrice+"￥</span></li>" +
                    "<li>折扣价:<span>"+product.promotionPrice+"￥</span></li>");
                $(".shop-detail").html(html);
                var list = data.extend.map.list;
                $.each(list,function (index,item) {
                    html = $("<img src='"+item.imgAddress+"' class='img-rounded col-sm-3'" +
                        "style='margin-left:3%;'/>");
                    $(".product-imgs").append(html);
                });

            }
        })
    }


    $(".user-buy").click(function () {
        window.location.href = "/order?productId="+productId;
    })

});