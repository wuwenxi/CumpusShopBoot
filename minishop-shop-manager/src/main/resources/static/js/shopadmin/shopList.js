$(function () {
    getShopList();

    function getShopList(){
        $.ajax({
            url:"/shop/getshoplist",
            type:"GET",
            success:function (data) {
                console.log(data);
                build_shop_list(data);
            }
        });
    }

    function build_shop_list(result) {
        //每次清除页面数据
        $("#shops tbody").empty();
        var shops = result.extend.map.shops;
        var enableStatusTd,editBtn,enterShop,editUpdate;
        //jQuery遍历元素
        $.each(shops,function (index,item) {
            if(item.enableStatus === -1){
                enableStatusTd = $("<td></td>").append("审核中");
                //按钮
                editBtn = $("<button disabled='disabled'></button>").addClass("btn btn-primary btn-xs").append("切换状态");
                editUpdate = $("<button disabled='disabled'></button>").addClass("btn btn-primary btn-xs").append("更新");
                enterShop = $("<button disabled='disabled'></button>").addClass("btn btn-primary btn-xs").append("进入店铺");
            }else {
                //若商品为上架状态，则显示下架
                var textOp = "休息中";
                var contraryStatus = 1;
                if (item.enableStatus ===1) {
                    textOp = "营业中";
                    contraryStatus = 0;
                } else {
                    contraryStatus = 1;
                }

                //取出每一列数据
                enableStatusTd = $("<td></td>").append(textOp);

                //按钮
                editBtn = $("<button></button>").addClass("btn btn-primary btn-xs enable_status").append("切换状态");
                editBtn.attr("shopId", item.shopId).attr("status", contraryStatus);
                editUpdate = $("<button></button>").addClass("btn btn-primary btn-xs update_shop").append("更新");
                editUpdate.attr("shopId", item.shopId);
                //制作表格  appendTo:将表添加到 emp_tbl 的 tbody当中
                enterShop = $("<button></button>").addClass("btn btn-info btn-xs enter").append("进入店铺");
                enterShop.attr("shopId",item.shopId);

            }
            var shopAddressTd = $("<td></td>").append(item.shopAddress);
            var shopIdTd = $("<td></td>").append(index+1);
            var shopNameTd = $("<td></td>").append(item.shopName);
            var btnTd = $("<td></td>").append(editBtn).append("   ").append(editUpdate).append("   ").append(enterShop);

            $("<tr></tr>")
                .append(shopIdTd)
                .append(shopNameTd)
                .append(shopAddressTd)
                .append(enableStatusTd)
                .append(btnTd)
                .appendTo("#shops tbody");

        });

    }

    $(document).on("click",".enable_status",function () {
        var shopId = $(this).attr("shopId");
        var status = $(this).attr("status");
        changeStatus(shopId,status);
    });
    $(document).on("click",".enter",function () {
        var shopId = $(this).attr("shopId");
        if(shopId!==0){
            window.location.href = "/shopAdmin/shopdetail?shopId="+shopId;
        }
    });
    $(document).on("click",".update_shop",function () {
        var shopId = $(this).attr("shopId");
        if(shopId>0){
            window.location.href = "/shopAdmin/modifyShop?shopId="+shopId;
        }
    });

    function changeStatus(id,status) {
        var shop={};
        shop.shopId=id;
        shop.enableStatus=status;
        $.ajax({
            url:"/shop/modifyshop",
            data:{shop:JSON.stringify(shop)},
            type:"POST",
            success:function (data) {
                if(data.code===100){
                    getShopList();
                }else {
                    alert("服务器内部错误");
                }
            }
        })
    }
});