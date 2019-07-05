
$(function () {
    getInitInfo();

    function getInitInfo() {
        $.getJSON("/shop/getshoplist",function (data) {
            $("#productFromShop").empty();
            var shop0 = "<option value='0'>—— 店铺 ——</option>";
            $("#productFromShop").append(shop0);
            $.each(data.extend.map.shops,function () {
                var shop = $("<option></option>").append(this.shopName).attr("value",this.shopId);
                $("#productFromShop").append(shop);
            })

        });
    }

    function getCategory(shopId){
        $.getJSON("/product/getcategorylist/"+shopId,function (data) {
                if (data.code===100){
                    $("#productCategory").empty();
                    $("#productCategory").append("<option value='0'>—— 商品类别 ——</option>");
                    $.each(data.extend.map.categoryList,function () {
                        var productCategory = $("<option></option>").append(this.productCategoryName)
                            .attr("value",this.productCategoryId);
                        $("#productCategory").append(productCategory);
                    })
                } else {
                    $("#modal_hint").text(data.extend.map.msg);
                    $("#modal").modal({
                        backdrop:'static'
                    });
                    $("#productCategory").append("<option value='0'>—— 商品类别 ——</option>");
                }
            }
        )
    }

    $("#productFromShop").change(function () {
        $("#productCategory").empty();
        var shopId = $("#productFromShop").val();
        if(shopId>0){
            getCategory(shopId);
        }else {
            var productCategory = "<option value='0'>—— 商品类别 ——</option>";
            $("#productCategory").append(productCategory);
        }
    });

    $("#submit").click(function () {
        var product={};

        product.productName = $("#productName").val();
        product.productDesc = $("#productDesc").val();
        product.shop={
            shopId:$("#productFromShop option:selected").val()
        };
        product.productCategory={
            productCategoryId:$("#productCategory option:selected").val()
        };
        product.promotionPrice=$("#promotionPrice").val();
        product.normalPrice=$("#normalPrice").val();

        if(!product.productName||!product.productDesc||!product.normalPrice||!product.promotionPrice){
            $("#modal_hint").text("请填写商品的完整信息！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        if(!product.shop){
            $("#modal_hint").text("请选择商品所属店铺！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        if(!product.productCategory){
            $("#modal_hint").text("请选择商品类别！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        var formdata = new FormData();

        var productImg = $("#productImg")[0].files[0];

        for (var i=1;i<6;i++){
            var detailImgs = $("#detailImg"+i)[0].files[0];
            if(!$.isEmptyObject(detailImgs)){
                formdata.append("productImgs"+i,detailImgs);
            }
        }

        formdata.append("productStr",JSON.stringify(product));
        formdata.append("productImg",productImg);

        $.ajax({
            url:"/product/addProduct",
            type:"POST",
            data:formdata,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.code === 200){
                    $("#modal_hint").text(data.extend.msg);
                    $("#modal").modal({
                        backdrop:'static'
                    })
                }
            }
        })
    })

});
