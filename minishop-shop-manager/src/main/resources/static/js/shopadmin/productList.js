
$(function () {

    var currentPage,theLastPages;

    $(function () {
        to_page(1);
    });

    function to_page(pn){
        $.getJSON("/product/getProductList/"+pn,function (data) {
            //console.log(data);
            if(data.code === 100){
                var result = data.extend.map;

                currentPage = result.productPageInfo.pageNum;

                theLastPages = result.productPageInfo.pages;

                build_product_list(result);

                build_page_info(result);

                build_page_nav(result);
            }else {
                alert(data.extend.msg);
            }
        });
    }
    function build_product_list(result) {

        $("#product tbody").empty();

        var product = result.productPageInfo.list;
        var statusTd,status,edit,preview;//状态，编辑，预览

        $.each(product,function (index,item) {

            //若商品为上架状态，则显示下架
            var textOp = "上架";
            var contraryStatus = 1;
            if (item.enableStatus ===1) {
                textOp = "在架";
                contraryStatus = 0;
            } else {
                contraryStatus = 1;
            }

            //取出每一列数据
            statusTd = $("<td></td>").append(textOp);

            //按钮
            status = $("<button></button>").addClass("btn btn-primary btn-xs enable_status")
                .append("切换状态").attr("productId", item.productId).attr("shopId",item.shop.shopId).attr("status", contraryStatus);
            //制作表格  appendTo:将表添加到 emp_tbl 的 tbody当中
            preview = $("<button></button>").addClass("btn btn-info btn-xs preview")
                .append("预览商品").attr("productId",item.productId);
            edit = $("<button></button>").addClass("btn btn-info btn-xs edit")
                .append("更新").attr("productId",item.productId);
            var btn = $("<td></td>").append(status).append("   ").append(preview).append("  ").append(edit);

            var productId = $("<td></td>").append(index+1);
            var productName = $("<td></td>").append(item.productName);
            var productDesc = $("<td></td>").append(item.productDesc);
            var shopName = $("<td></td>").append(item.shop.shopName);
            var productCategoryName = $("<td></td>").append(item.productCategory.productCategoryName);

            $("<tr></tr>")
                .append(productId).append(productName).append(productDesc).append(productCategoryName)
                .append(shopName).append(statusTd).append(btn)
                .appendTo("#product tbody");

        })
    }
    
    function build_page_info(result) {

        $("#page_info").empty();

        var pages = result.productPageInfo.pages;//总共的页码
        var pageNum = result.productPageInfo.pageNum;//当前页码
        var total = result.productPageInfo.total;//总记录数

        $("#page_info").append("当前第"+pageNum+"页，共"+pages+"页，共"+total+"条记录");
    }

    function build_page_nav(result) {
        $("#page_nav").empty();

        var list = result.productPageInfo.navigatepageNums;//导航页码

        var ul = $("<ul></ul>").addClass("pagination");
        var firstPage = $("<li></li>").append($("<a></a>").append("首页"));
        var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));


        var Previous = $("<li></li>").append($("<a></a>").append("&laquo;"));
        var lastPage = $("<li></li>").append($("<a></a>").append("末页"));

        if(result.productPageInfo.hasPreviousPage === false){
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

        if(result.productPageInfo.hasNextPage === false){
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

    $(document).on("click",".enable_status",function () {
        var productId = $(this).attr("productId");
        var status = $(this).attr("status");
        if(productId>0 && status!=null){
            var product ={};
            product.productId = productId;
            product.enableStatus = status;
            product.shop={
                shopId:$(this).attr("shopId")
            };
            $.ajax({
                url:"/product/modifyProduct",
                data:{product:JSON.stringify(product)},
                type:"POST",
                success:function () {
                    to_page(currentPage);
                }
            })
        }
    });
    $(document).on("click",".preview",function () {
        alert("preview")
    });
    $(document).on("click",".edit",function () {
        var productId = $(this).attr("productId");
        if(productId>0){
            window.location.href = "/shopAdmin/modifyProduct?productId="+productId;
        }
    });
});