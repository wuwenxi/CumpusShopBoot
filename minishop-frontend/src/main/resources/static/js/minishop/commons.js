/*取消点击出现下拉菜单，改为点击跳转页面*/
$(document).ready(function(){
    $(document).off('click.bs.dropdown.data-api');
});

$(function(){

    initShopCategory();

    function initShopCategory(){
    	$.ajax({
			url:"/frontend/initShopCategory",
			type:"GET",
            contentType:false,
            processData:false,
            cache:false,
			success:function (data) {
                $.each(data.extend.map.categoryList, function(index, item) {
					for (var i=1;i<9;i++){
                        if(item.parent.shopCategoryId === i){
                            var html = $("<li>" +
                                "<a href='/frontend/shopList?shopCategoryId="+item.shopCategoryId+"' target='_blank'>"
                                +item.shopCategoryName+"</a>" +
                                "</li>");
                            $(".index"+i).append(html);
                        }
                    }
                });
            }
		})
	}

    initUserInfo();

    function initUserInfo(){
        var personInfo;
        $.getJSON("/personInfo/initUserInfo",function (data) {
            if(data.code === 100){
                personInfo = data.extend.personInfo;
                //初始化用户信息
                if(personInfo !== null){
                    $(".headerTopLeft").css("display","none");
                    $(".personInfo").css("display","block");

                    var userType;
                    if(personInfo.userType===1){
                        userType = "店家";
                    }else {
                        userType = "普通用户"
                    }
                    var html;
                    if(personInfo.profileImg!==null){
                        html = $("<img src='/image/"+personInfo.profileImg+"' class='img-circle img-thumbnail'>" +
                            "<span>欢迎"+personInfo.userName+","+userType+"</span>")
                    }else {
                        if(personInfo.gender === "男"){
                            html = $("<img src='/image/man.jpg' class='img-circle img-thumbnail'>" +
                                "<span>欢迎"+personInfo.userName+","+userType+"</span>")
                        }else {
                            html = $("<img src='/image/woman.jpg' class='img-circle img-thumbnail'>" +
                                "<span>欢迎"+personInfo.userName+","+userType+"</span>")
                        }
                    }
                    $("#userImg").html(html);
                }
            }
            $(".registerShop").click(function () {
                /**
                 *    如果没有登录  跳转登录页面
                 *    如果登录了 用户类别不为店家  则跳转注册页面
                 */
                if(personInfo !== undefined) {
                    if (personInfo.userType === 1) {
                        $("#modal_hint").text("您已是店家，不可以申请了");
                        $("#modal").modal({
                            backdrop: 'static'
                        });
                        $("#message_dismiss").click(function () {
                            window.location.href = "/";
                        })
                    }else {
                        window.location.href = "/registerShop?userId="+personInfo.userId;
                    }
                }else {
                    $("#modal_hint").text("您还未登录,请先登录");
                    $("#modal").modal({
                        backdrop: 'static'
                    });
                    $("#message_dismiss").click(function () {
                        window.location.href = "/userLogin";
                    })
                }
            });
        });
    }

    /*$("#search_from").typeahead({
        source: function (query, process) {
            return $.ajax({
                url: '/frontend/searchWithName?name='+query,
                type: 'GET',
                success: function (result) {
                    console.log(result);
                    process(result);
                }
            });
        }
    });*/


    $("#userImg").click(function(){
        alert("...")
    });


	$("#search").click(function(){
		alert("正在检索...")
	});

});

