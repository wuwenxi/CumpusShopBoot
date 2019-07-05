//解析时间格式
function fmtDate(time){
    var data = new Date(time);
    var year = data.getFullYear();  //获取年
    var month = data.getMonth() + 1;    //获取月
    var day = data.getDate(); //获取日
    var hours = data.getHours();
    var minutes = data.getMinutes();

    time = year + "年" + month + "月" + day + "日" + " " + hours + ":" + minutes;
    return time;
}

$(function () {

    var currentPage,theLastPages;

    $(function () {
        to_page(1)
    });

    function to_page(pn) {
        $.getJSON("/product/getProductCategoryList/"+pn,function (data) {
            var productCategory = data.extend.map.productCategoryPageInfo;
            currentPage = productCategory.pageNum;

            theLastPages = productCategory.pages;

            build_productCategory_list(productCategory);

            build_page_info(productCategory);

            build_page_nav(productCategory);
        })
    }


    function build_productCategory_list(productCategory) {
        $("#productCategory tbody").empty();

        var category = productCategory.list;
        var edit,del;//状态，编辑，预览

        $.each(category,function (index,item) {

            edit = $("<button></button>").addClass("btn btn-info btn-xs edit")
                .append("更新").attr("productCategoryName",item.productCategoryName)
                .attr("productCategoryId",item.productCategoryId);
            del = $("<button></button>").addClass("btn btn-info btn-xs del")
                .append("删除").attr("productCategoryId",item.productCategoryId);
            var btn = $("<td></td>").append(edit).append("  ").append(del);

            var categoryId = $("<td></td>").append(index+1);
            var categoryName = $("<td></td>").append(item.productCategoryName);
            var createTime = $("<td></td>").append(fmtDate(item.createTime));
            var shopName = $("<td></td>").append(item.shop.shopName);

            $("<tr></tr>")
                .append(categoryId).append(categoryName).append(createTime)
                .append(shopName).append(btn)
                .appendTo("#productCategory tbody");

        })
    }

    function build_page_info(productCategory) {
        $("#page_info").empty();

        var pages = productCategory.pages;//总共的页码
        var pageNum = productCategory.pageNum;//当前页码
        var total = productCategory.total;//总记录数

        $("#page_info").append("当前第"+pageNum+"页，共"+pages+"页，共"+total+"条记录");
    }

    function build_page_nav(productCategory) {
        $("#page_nav").empty();

        var list = productCategory.navigatepageNums;//导航页码

        var ul = $("<ul></ul>").addClass("pagination");
        var firstPage = $("<li></li>").append($("<a></a>").append("首页"));
        var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));


        var Previous = $("<li></li>").append($("<a></a>").append("&laquo;"));
        var lastPage = $("<li></li>").append($("<a></a>").append("末页"));

        if(productCategory.hasPreviousPage === false){
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

        if(productCategory.hasNextPage === false){
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

    $(document).on("click",".edit",function () {
        $("#category_id").empty();
        $("#category_id").append($(this).attr("productCategoryId"));
        $("#category_name").val($(this).attr("productCategoryName"));
        $("#category_update_modal").modal({
            backdrop:'static'
        })
    });

    $("#category_update_save").click(function () {
        var productCategoryName = $("#category_name").val();
        if(!productCategoryName){
            return;
        }

        var  productCategory={};

        productCategory.productCategoryId = $("#category_id").val();
        productCategory.productCategoryName = $("#category_name").val();
        var formdata = new FormData();

        formdata.append("productCategory",productCategory);

        $.ajax({
            url:"/product/modifyProductCategory",
            type:"PUT",
            data:formdata,
            success:function (data) {
                if(data.code === 100){
                    //alert("更新成功！");
                    $("#category_update_modal").modal('hide');
                    to_page(currentPage);
                }
            }
        })
    });

    $(document).on("click",".del",function () {
        $("#delete_hint").text("是否删除该类别?");
        $("#delete_modal").modal({
            backdrop:'static'
        });

        var productCategoryId = $(this).attr("productCategoryId")
        $("#delete_dismiss").click(function () {
            $.ajax({
                url:"/product/deleteProductCategory/"+productCategoryId,
                type:"DELETE",
                success:function (data) {
                    if(data.code===100){
                        $("#delete_modal").modal('hide');
                        to_page(currentPage);
                    }
                    alert(data.extend.map.msg);
                }
            })
        })
    })
});