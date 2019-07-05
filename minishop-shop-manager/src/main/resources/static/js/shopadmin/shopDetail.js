$(function () {

    var shopId = getQueryString("shopId");

    $(function () {
        to_page(1);
    });

    function to_page(pn) {
        $.ajax({
            url:"/product/getProductList/"+pn,
            type:"GET",
            data:"shopId="+shopId,
            success:function (data) {
                if(data.code === 100){
                    console.log(data)
                }else {
                    alert(data.extend.map.msg);
                }
            }
        })
    }
});