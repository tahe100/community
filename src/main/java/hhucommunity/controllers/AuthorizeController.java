package hhucommunity.controllers;

import hhucommunity.dto.AccessTokenDTO;
import hhucommunity.dto.GithubUser;
import hhucommunity.mapper.UserMapper;
import hhucommunity.model.CommunityUser;
import hhucommunity.provider.GithubProvider;
import hhucommunity.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired//和component配合使用
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//使用配置文件来装clientId
                                 // then you don't have to write  accessTokenDTO.setClient_id("c7bd086943ffebd8461e");
                                 // you can use  accessTokenDTO.setClient_id(clientId);
    /*Use a configuration file to store the clientId value, so you don't have to hardcode it in the code. */
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,//Session 是在request里拿到的
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //用java模拟post请求(httpclient很复杂)
        //用okhttp
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            //Login successful

            CommunityUser communityUser = new CommunityUser();
            //快捷键 command+alt+v 抽取变量
            String token = UUID.randomUUID().toString();
            communityUser.setToken(token);
            communityUser.setName(githubUser.getName());
            communityUser.setAccountId(String.valueOf(githubUser.getId()));
            communityUser.setAvatarUrl(githubUser.getAvatarUrl());
            System.out.println(communityUser.toString());
            //写 cookie 和session
            //用数据库实物的储存代替了session
            userService.createOrUpdate(communityUser);
            //request.getSession().setAttribute("user",githubUser);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";//如果return "index" 虽然还是会回到首页但是地址是
                                    // http://localhost:8080/callback?code=8daea70246ad70daeb3f&state=1
                                    //只有 redirect:/
        }else{
            //Login failed. Please login again.
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //删除session
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }



}
