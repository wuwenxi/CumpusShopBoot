$(function () {

    $("#submit").click(function () {
      var personInfo = {};
      personInfo.name = $("#name").val();
      personInfo.userName = $("#userName").val();
      personInfo.password = $("#password").val();
      personInfo.email = $("#email").val();
      personInfo.phone = $("#phone").val();
      personInfo.gender = $("input[name=gender]").val();


      if (!personInfo.name || !personInfo.userName || !personInfo.password
          ||!personInfo.email||!personInfo.gender||!personInfo.phone){
          modal("请填写完整用户信息");
          return;
      }
      var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
      if(!regEmail.test(personInfo.email)){
          modal("请填写正确的邮箱");
          return;
      }
      var phone = /^1[34578]\d{9}$/;
      if(!phone.test(personInfo.phone)){
          modal("请填写正确的电话号码")
          return;
      }
      var userImg = $("#userImg")[0].files[0];

      var formdata = new FormData();

      formdata.append("personInfo",JSON.stringify(personInfo));
      if(userImg){
          formdata.append("userImg",userImg);
      }

      $.ajax({
          url:"/personInfo/userRegister",
          data:formdata,
          type:"POST",
          cache:false,
          contentType:false,
          processData:false,
          success:function (data) {
              if(data.code === 100){
                  $("#modal_hint").text("注册成功");
                  $("#modal").modal({
                      backdrop:"static"
                  });
                  $("#message_dismiss").click(function () {
                      window.location.href = "/";
                  })
              }else {
                  modal("注册失败");
              }
          }
      })
    });

    function modal(text) {
        $("#modal_hint").text(text);
        $("#modal").modal({
            backdrop:"static"
        });
        $("#message_dismiss").click(function () {
            window.location.href = "/register";
        })
    }
});