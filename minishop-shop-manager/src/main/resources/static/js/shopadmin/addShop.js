/*$('#target').distpicker();*/

$(function () {
    function getShopInitInfo(parentId){
        $.ajax({
            url:"/shop/getshopinitinfo/"+parentId,
            type:"GET",
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                $("#shopCategory").empty();
                var shopCategoryList = data.extend.map.shopCategoryList;
                var option0 = $("<option></option>").append("—— 子类别 ——").attr("value",0);
                $(option0).appendTo("#shopCategory");
                $.each(shopCategoryList,function () {
                    var option = $("<option></option>").append(this.shopCategoryName)
                        .attr("value",this.shopCategoryId);
                    $(option).appendTo("#shopCategory");
                });
            }
        })
    }

    /**/
    $("#select1").change(function () {
        var parentId = $("#select1").val();
        if(parentId>0){
            getShopInitInfo(parentId)
        }else {
            $("#shopCategory").empty();
            var shopCategory = "<option value='0'>—— 子类别 ——</option>";
            $("#shopCategory").append(shopCategory);
        }
    });

    $("#submit").click(function () {
        var shop = {};
        shop.shopName = $("#shopName").val();
        shop.shopDesc = $("#shopDesc").val();

        shop.area ={
            areaId:$("#area option:selected").val()
        };
        shop.shopAddress = $("#shopAddress").val();
        shop.phone = $("#phone").val();
        shop.shopCategory={
            shopCategoryId:$("#shopCategory option:selected").val()
        };

        var formdata = new FormData();
        var shopImg = $(".shopImg")[0].files[0];

        if(!shop.shopName||!shop.shopAddress||!shop.shopCategory){
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
        formdata.append("shopStr",JSON.stringify(shop));

        $.ajax({
            url:"/shop/registershop",
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
                    alert(data.extend.msg);
                }
            }
        })
    })

});
