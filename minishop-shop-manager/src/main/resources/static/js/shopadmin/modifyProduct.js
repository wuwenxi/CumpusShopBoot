$(function () {
   
    
    var productId = getQueryString("productId");
    
    if(productId>0){
        initInfoProduct(productId);
    }

    function initInfoProductCategory(shopId,productId) {
        $.getJSON("/product/getcategorylist/"+shopId,function (data) {
            $("#productCategory").empty();
            var productCategory = data.extend.map.categoryList;
            var productCategory1 = "<option value='0'>—— 商品类别 ——</option>";
            $("#productCategory").append(productCategory1);
            $.each(productCategory,function (index,item) {
                var productCategory = $("<option></option>").append(item.productCategoryName)
                    .attr("value",item.productCategoryId);
                productCategory.appendTo("#productCategory");
            });

            $("#productCategory").find("option[value='"+productId+"']").attr("selected",true);
        })
    }

    function initInfoProduct(productId) {
        $.getJSON("/product/getProduct/"+productId,function (data) {
            console.log(data);
            if(data.code === 100){
                var product = data.extend.map.product;

                $("#productName").val(product.productName);
                $("#promotionPrice").val(product.promotionPrice);
                $("#normalPrice").val(product.normalPrice);
                $("#productDesc").val(product.productDesc);
                $("#productFromShop").empty();
                var shop = $("<option></option>").append(product.shop.shopName)
                    .attr("value",product.shop.shopId);
                $("#productFromShop").append(shop);

                initInfoProductCategory(product.shop.shopId,product.productId);
            }

        });
    }

    $("#submit").click(function () {
        var product={};

        product.productId = productId;
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

        formdata.append("product",JSON.stringify(product));
        formdata.append("productImg",productImg);

        $.ajax({
            url:"/product/modifyProduct",
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
                window.location.href="/shopAdmin/productlist"
            }
        })
    })
});