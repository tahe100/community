package hhucommunity.controllers;

import hhucommunity.dto.AccessTokenDTO;
import hhucommunity.dto.GithubUser;
import hhucommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //用java模拟post请求(httpclient很复杂)
        //用okhttp
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }



}
