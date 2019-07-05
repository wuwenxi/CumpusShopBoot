$(function () {

    var parentId = 6;
    var currentPage,theLastPages;

    to_page(1);

    //获取当前父类别下的所有店铺
    function initShopWithParentId(pn) {
        $.ajax({
            url:"/frontend/initShopWithParentId/"+parentId,
            data:"pn="+pn,
            type:"GET",
            cache:false,
            contentType:false,
            processData:false,
            success:function (data) {
                if(data.code === 100){
                    var result = data.extend.map;

                    currentPage = result.pageInfo.pageNum;

                    theLastPages = result.pageInfo.pages;

                    build_shop_list(result);

                    build_page_info(result);

                    build_page_nav(result);
                }else {
                    $("#modal_hint").text("当前类别没有店铺");
                    $("#modal").modal({
                        backdrop:'static'
                    });
                }
            }
        })
    }

    function build_shop_list(result) {
        $(".shop").empty();
        var shopList = result.pageInfo.list;
        $.each(shopList,function (index,item) {
            /* &nbsp;&nbsp;&nbsp; */
            var html = $("<div class='col-sm-6 col-md-3 shop-list'>"
                +"<a href='/frontend/shopDetail?shopId="+item.shopId+"' " +
                "title='"+"培训教育"+" "+item.shopCategory.shopCategoryName
                +" "+item.shopName+"'>"
                +"<div class='thumbnail'>"
                + "<img src='"+item.shopImg+"' >"
                +"<div class='caption'>"
                +"<h4>"+"培训教育"+" "+item.shopCategory.shopCategoryName
                +" "+item.shopName+"</h4>"
                +"</div>"
                +"</div>"
                + "</a>"
                + "</div>");

            $(".shop").append(html);
        })
    }

    function build_page_info(result) {
        $("#page_info").empty();

        var pages = result.pageInfo.pages;//总共的页码
        var pageNum = result.pageInfo.pageNum;//当前页码
        var total = result.pageInfo.total;//总记录数

        $("#page_info").append("当前第"+pageNum+"页，共"+pages+"页，共"+total+"条记录");
    }

    function build_page_nav(result) {
        $("#page_nav").empty();

        var list = result.pageInfo.navigatepageNums;//导航页码

        var ul = $("<ul></ul>").addClass("pagination");
        var firstPage = $("<li></li>").append($("<a></a>").append("首页"));
        var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));


        var Previous = $("<li></li>").append($("<a></a>").append("&laquo;"));
        var lastPage = $("<li></li>").append($("<a></a>").append("末页"));

        if(result.pageInfo.hasPreviousPage === false){
            firstPage.addClass("disabled");
            Previous.addClass("disabled");
        }else {
            firstPage.click(function () {
                to_page(1);
            });
            Previous.click(function () {
                to_page(currentPage-1);
            });
        }

        if(result.pageInfo.hasNextPage === false){
            nextPage.addClass("disabled");
            lastPage.addClass("disabled");
        }else {
            nextPage.click(function () {
                to_page(currentPage+1);
            });
            lastPage.click(function () {
                to_page(theLastPages);
            })
        }

        ul.append(firstPage)
            .append(Previous)
            .appendTo("#page_nav");

        $.each(list,function (index,item) {
            var num = $("<li></li>").append($("<a></a>").append(item));

            if(currentPage === item){
                num.addClass("active");
            }

            num.click(function () {
                to_page(item);
            });

            ul.append(num).appendTo("#page_nav");
        });


        ul.append(nextPage)
            .append(lastPage);

        $("<nav></nav>").append(ul).appendTo("#page_nav");
    }

    function to_page(pn) {
        initShopWithParentId(pn);
    }
});