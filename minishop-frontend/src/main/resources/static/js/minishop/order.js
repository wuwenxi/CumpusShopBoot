
$(function () {

    var productId = getQueryString("productId");

    if(productId>0){
        getProductDetail(productId);
    }

    function getProductDetail() {
        $.getJSON("/frontend/getProduct/"+productId,function (data) {
            var product = data.extend.map.product;
            var checkBokTd = $("<td><input type='checkbox' class='check_item'></td>");
            var productName = $("<td></td>").append(product.productName);
            var price = product.promotionPrice - 0;
            var promotionPrice = $("<td></td>").append(price.toFixed(2));
            var count = $("<td><button class=\"min\" type=\"button\" style=\"width: 12px\">-</button>\n" +
                "<input class=\"text_box\" name=\"goodnum\" type=\"text\" value=\"1\" style=\"width:25px;\" />\n" +
                "<button class=\"add\" type=\"button\" style=\"width: 12px\">+</button></td>");
            var discount = product.promotionPrice - product.normalPrice;
            var normalPrice;
            if(discount === 0){
                normalPrice =$("<td></td>").append("-")
            }else {
                normalPrice =$("<td></td>").append(discount.toFixed(2));
            }
            price = product.normalPrice - 0;
            var sum = $("<td class='price'></td>").append(price.toFixed(2));
            var btnTd = $("<td></td>").append($("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除"));
            $("<tr></tr>").append(checkBokTd)
                .append(productName)
                .append(promotionPrice)
                .append(count)
                .append(normalPrice)
                .append(sum)
                .append(btnTd)
                .appendTo("#pay tbody");
        });
    }

    //全选与全不选
    $("#check_all").click(function () {
        //将商品勾选上
        //attr获取自定义属性值  prop获取dom原生属性值
        //$(this).prop("checked")  当前勾选框是否别选中
        $(".check_item").prop("checked",$(this).prop("checked"));
        show("#check_all");
    });

    $(document).on("click",".check_item",function () {
        //将全选框勾上
        $("#check_all").prop("checked",$(this).prop("checked"));
        show("#check_all");
    });

    //数量增加
    $(document).on("click",".add",function () {
        // $(this).prev() 就是当前元素的前一个元素，即 text_box
        $(this).prev().val(parseInt($(this).prev().val()) + 1);
        show("#check_all");
    });
    $(document).on("click",".min",function () {
        if(parseInt($(this).next().val())===1){
            //alert("最少一件商品");
            $("#modal_hint").text("最少购买一件商品");
            $("#modal").modal({
                backdrop:'static'
            });
        }else {
            $(this).next().val(parseInt($(this).next().val()) - 1);
            show("#check_all");
        }
    });

    //显示价格
    function show(d) {
        if ($(d).prop("checked")) {
            var goodNum = $(".text_box").val();
            var price = $(".price").text();
            //保留两位小数
            $(".amount span").html("￥" + (goodNum * price).toFixed(2));
            $("#count").html(""+goodNum);
            //移除按钮禁用状态
            $(".pay").find("*").removeClass("disabled");
        } else {
            $(".amount span").html("￥0");
            $("#count").html("0件");
            //添加按钮禁用状态
            //$(".pay button").prop("disabled",true);
            $(".pay button").addClass("disabled");
        }
    }

    //支付按钮
    $(".pay button").click(function () {
        //清空表单
        $("#order_modal form")[0].reset();
        reset_form();

        //填充商品名和商品数量
        $("#product").attr("placeholder",$("tbody td:eq(1)").text());
        $("#count").attr("placeholder",$(".text_box").val()+"件");
        $("#order_modal").modal({
            backdrop:"static"
        });
    });

    //清空样式
    function reset_form(){
        $("#order_modal form").find("*").removeClass("has-success has-error");
        $("#order_modal form").find(".help-block").text("");
    }

    //支付按钮
    $("#submit").click(function () {
        reset_form();
        var name = $("#name").val();
        if(name===""){
            show_validate_msg("#name","error","请输入收货人姓名");
            return false;
        }
        var regPhone = /^[1][3,4,5,7,8][0-9]{9}$/;
        var phone = $("#phone").val();
        if(phone === ""){
            show_validate_msg("#phone","error","请输入收货人电话");
            return false;
        }
        if(!regPhone.test(phone)){
            show_validate_msg("#phone","error","请输入正确的电话号码");
            return false;
        }
        var address = $("#address").val();
        if(address===""){
            show_validate_msg("#address","error","请输入收货人地址");
            return false;
        }

        var order = {};
        order.name = name;
        order.phone = phone;
        order.address = address;
        order.product = {
            productId:productId
        };
        order.productCount = $(".text_box").val();
        var formdata = new FormData();
        formdata.append("order",JSON.stringify(order));

        $.ajax({
            url:"/order/createOrder",
            data:formdata,
            type:"POST",
            contentType:false,
            processData:false,
            cache:false,
            success:function (result) {
                $("#order_modal").modal("hide");
                if(result.code === 100){
                    $("#modal_hint").text("购买成功！");
                    $("#modal").modal({
                        backdrop:"static"
                    })
                    $("#message_dismiss").click(function () {
                        window.location.href = "/frontend/productDetail?product="+productId;
                    })
                }else {
                    $("#modal_hint").text("购买失败！");
                    $("#modal").modal({
                        backdrop:"static"
                    })
                }

            }
        })

    });

    function show_validate_msg(ele,status,msg){
        //清空表单内容
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");

        if("success"===status){
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if("error"===status){
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }


});