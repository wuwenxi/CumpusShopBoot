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

    $("#submit").click(function () {

        var productCategory={};

        productCategory.productCategoryName = $("#productCategoryName").val();
        productCategory.shop = {
            shopId:$("#productFromShop option:selected").val()
        };

        if(!productCategory.productCategoryName||!productCategory.shop){
            alert("请填写完整信息");
            return;
        }

        var formdata = new FormData();

        formdata.append("productCategory",JSON.stringify(productCategory));

        $.ajax({
            url:"/product/addProductCategory",
            type:"POST",
            data:formdata,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                //console.log(data);
                if(data.code === 100){
                    alert("提交成功");
                    window.location.href = "/shopAdmin/productcategory";
                }else {
                    alert(data.extend.map.msg)
                }
            }
        })
    })
});