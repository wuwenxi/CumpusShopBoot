 $(function () {

    var shopCategoryId = getQueryString("shopCategoryId");
    var currentPage,theLastPages;
    if(shopCategoryId>0){
        to_page(1);
    }
    function to_page(pn) {
        initWithShopCategory(pn);
    }

    function initWithShopCategory(pn) {
        var location,locationName;

        $.ajax({
            url:"/frontend/initWithShopCategory/"+shopCategoryId,
            data:"pn="+pn,
            type:"GET",
            cache:false,
            processData:false,
            contentType:false,
            success:function (data) {
                console.log(data);
                if(data.code === 100){
                    var shopCategory = data.extend.map.shopCategory;
                    switch (shopCategory.parent.shopCategoryId){
                        case 1:
                            location = "food";
                            locationName = "美食饮品";
                            break;
                        case 2:
                            location = "second_market";
                            locationName = "二手市场";
                            break;
                        case 3:
                            location = "rent_market";
                            locationName = "租赁市场";
                            break;
                        case 4:
                            location = "entertainment";
                            locationName = "休闲娱乐";
                            break;
                        case 5:
                            location = "hairdressing";
                            locationName = "美容美发";
                            break;
                        case 6:
                            location = "education";
                            locationName = "培训教育";
                            break;
                        case 7:
                            location = "exercise";
                            locationName = "运动健身";
                            break;
                        case 8:
                            location = "other";
                            locationName = "其他";
                            break;
                    }
                    var parentHtml = $("<li><a href='/frontend/"+location+"' >"+locationName+"</a></li>");
                    var html = $("<li class='active'>"+shopCategory.shopCategoryName+"</li>");
                    $(".location").append(parentHtml).append(html);

                    //展示商品

                    var result = data.extend.map;

                    var list = data.extend.map.pageInfo.list;

                    if(list !== null && list.length > 0 ){

                        currentPage = result.pageInfo.pageNum;

                        theLastPages = result.pageInfo.pages;

                        build_shop_list(list);

                        build_page_info(result);

                        build_page_nav(result);
                    }else {
                        //当前类别没有店铺
                        $("#modal_hint").text("当前类别没有店铺");
                        $("#modal").modal({
                            backdrop:'static'
                        });
                    }
                }

                function build_shop_list(list) {
                    $.each(list,function (index,item) {
                        var html = $("<div class='col-sm-6 col-md-3 shop-list'>"
                            +"<a href='/frontend/shopDetail?shopId="+item.shopId+"' " +
                            "title='"+locationName+" "+item.shopCategory.shopCategoryName
                            +" "+item.shopName+"'>"
                            +"<div class='thumbnail'>"
                            + "<img src='"+item.shopImg+"' >"
                            +"<div class='caption'>"
                            +"<h4>"+locationName+" "+item.shopCategory.shopCategoryName
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
            }
        })
    }





});