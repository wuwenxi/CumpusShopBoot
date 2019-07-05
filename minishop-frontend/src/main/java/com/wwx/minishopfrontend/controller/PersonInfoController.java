package com.wwx.minishopfrontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.wwx.minishop.utils.InsertImageUtils.addPersonInfoImg;


@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    RestTemplate restTemplate;

    private static final String REST_URL_PERSON_PREFIX = "http://MINISHOP-PERSON-SERVICE/personInfo";

    private static final String REST_URL_LOCAL_PREFIX = "http://MINISHOP-PERSON-SERVICE/localAuth";

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/initUserInfo")
    public Msg initUser(HttpServletRequest request){
        //System.out.println("初始化用户...");
        //从验证信息中获取用户名  如果没有用户名 则用户为登录
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if(personInfo!=null){
            return Msg.success().add("personInfo",personInfo);
        }
        //从验证信息中获取用户信息  若用户未登录  则抛出异常返回给前端
        try {
            String name = request.getUserPrincipal().getName();
            if(name!=null){
                personInfo = restTemplate.getForObject(REST_URL_PERSON_PREFIX + "/findPersonInfoWithName/" + name, PersonInfo.class);
                if(personInfo!=null){
                    request.getSession().setAttribute("personInfo",personInfo);
                    return Msg.success().add("personInfo",personInfo);
                }
            }
            return Msg.fail();
        } catch (Exception e) {
            return Msg.fail();
        }
    }

    @PostMapping("/userRegister")
    public Msg register(HttpServletRequest request){
        String personInfoStr = HttpServletRequestUtils.getString(request,"personInfo");
        PersonInfo personInfo;
        try {
            personInfo = objectMapper.readValue(personInfoStr,PersonInfo.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        if(personInfo == null){
            return Msg.fail().add("msg","用户信息为空，请填写用户信息");
        }

        //验证用户名是否已被注册
        if(personInfo.getUserName()!=null){
            PersonInfo info = restTemplate.getForObject(REST_URL_PERSON_PREFIX + "/findPersonInfoWithName/" + personInfo.getUserName(), PersonInfo.class);
            if(info!=null){
                return Msg.fail().add("msg","当前用户名已注册");
            }
        }

        //解析图片
        MultipartFile userImage = null;
        CommonsMultipartResolver resolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断是否有文件上传
            if(resolver.isMultipart(request)){
                MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
                userImage = servletRequest.getFile("userImg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         *         先将用户信息存入数据库，如果保存成功 再判断是否有图片上传进行用户更新
         *         判断是否有图片上传
         *          1.有图片上传，添加图片地址，添加用户
         *          2.没有图片，直接添加用户
         */
        Boolean flag;
        if(userImage!=null){
            if(!ValidateUtil.checkShopImage(userImage.getOriginalFilename())){
                return Msg.fail().add("msg","文件格式错误！请传入图片");
            }
            try {
                addPersonInfoImg(personInfo,new ImageHolder(userImage.getName(),userImage.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        flag = restTemplate.postForObject(REST_URL_PERSON_PREFIX+"/addPersonInfo",personInfo,Boolean.class);

        if(flag!=null && flag){
            return Msg.success();
        }else {
            return Msg.fail().add("msg","注册失败");
        }
    }


    @PostMapping("/registerShopAdmin")
    public Msg registerShopAdmin(HttpServletRequest request){
        String localAuthStr = HttpServletRequestUtils.getString(request,"localAuth");
        LocalAuth localAuth;
        try {
            localAuth = objectMapper.readValue(localAuthStr,LocalAuth.class);
        } catch (Exception e) {
            //e.printStackTrace();
            return Msg.fail().add("msg","服务器内部错误");
        }

        if(localAuth == null || localAuth.getPersonInfo().getUserId() == null){
            return Msg.fail().add("msg","本地账户信息为空");
        }

        if(localAuth.getUserName()!=null){
            LocalAuth auth = restTemplate.getForObject(REST_URL_LOCAL_PREFIX + "/findLocalAuthWithName/" + localAuth.getUserName(), LocalAuth.class);
            if(auth!=null){
                return Msg.fail().add("msg","当前账户已被注册");
            }
        }

        Boolean flag = restTemplate.postForObject(REST_URL_LOCAL_PREFIX+"/addLocalAuth",localAuth,Boolean.class);
        if(flag!=null && flag){
            //用户类别更新为店家
            PersonInfo personInfo = restTemplate.getForObject(REST_URL_PERSON_PREFIX + "/findPersonInfoWithUserId/" + localAuth.getPersonInfo().getUserId(), PersonInfo.class);
            if(personInfo!=null){
                personInfo.setUserType(1);
                flag = restTemplate.postForObject(REST_URL_PERSON_PREFIX+"/modifyPersonInfo",personInfo,Boolean.class);
                if(flag!=null&&flag)
                    request.setAttribute("personInfo",personInfo);
                    return Msg.success();
            }
        }
        return Msg.fail().add("msg","注册失败！");
    }
}
