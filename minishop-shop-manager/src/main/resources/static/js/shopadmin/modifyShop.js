$(function () {

    var shopId = getQueryString("shopId");

    if(shopId>0){
        initShopInfo(shopId);
    }

    function initShopInfo(id){
        $.getJSON("/shop/getshop/"+id,function (data) {
            console.log(data);
            if(data.code === 100){
                var shop = data.extend.map.shop;
                $("#shopName").val(shop.shopName);
                $("#shopDesc").val(shop.shopDesc);
                $("#phone").val(shop.phone);
            }
        })
    }

    $("#submit").click(function () {
        var shop = {};
        shop.shopId = shopId;
        shop.shopName = $("#shopName").val();
        shop.shopDesc = $("#shopDesc").val();
        shop.phone = $("#phone").val();

        var formdata = new FormData();
        var shopImg = $(".shopImg")[0].files[0];

        if(!shop.shopName||!shop.shopDesc){
            $("#modal_hint").text("请填写完整店铺信息！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        var regPhone = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!shop.phone){
            $("#modal_hint").text("请填写电话号码！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }
        if(!regPhone.test(shop.phone)) {
            $("#modal_hint").text("请填写正确的电话号码！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        if (!shopImg){
            $("#modal_hint").text("请上传店铺图片！");
            $("#modal").modal({
                backdrop:'static'
            });
            return;
        }

        //封装数据
        formdata.append("shopImg",shopImg);
        formdata.append("shop",JSON.stringify(shop));

        $.ajax({
            url:"/shop/modifyshop",
            data:formdata,
            type:"POST",
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                console.log(data);
                if(data.code===100){
                    //alert("提交成功");
                    window.location.href = "/shopAdmin/shoplist"
                }else {
                    alert(data.extend.map.msg);
                }
            }
        })
    })
});
