
$(function () {

    $.getJSON("/personInfo/initPersonInfo",function (data) {
        if(data.code === 100){
            var person = data.extend.map.personInfo;
            if(!person.profileImg){
                if(person.gender ==="男"){
                    var html = "<img class='nav-user-photo' src='/image/man.jpg' />"
                    +"<span class='user-info'>" +
                        "<small>欢迎光临,</small>" +person.name+
                        "</span>"
                    +"<i class='icon-caret-down'></i>";
                    $("#userImg").html(html);
                }else {
                    html = "<img class='nav-user-photo' src='/image/woman.jpg' />"
                        +"<span class='user-info'>" +
                        "<small>欢迎光临,</small>" +person.name+
                        "</span>"
                        +"<i class='icon-caret-down'></i>";
                    $("#userImg").html(html);
                }
            }else {
                html = "<img class='nav-user-photo' src='"+person.profileImg+"' />"
                    +"<span class='user-info'>" +
                    "<small>欢迎光临,</small>" +person.name+
                    "</span>"
                    +"<i class='icon-caret-down'></i>";
                $("#userImg").html(html);
            }
        }
    })
});