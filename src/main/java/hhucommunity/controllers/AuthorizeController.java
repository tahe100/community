package hhucommunity.controllers;

import hhucommunity.dto.AccessTokenDTO;
import hhucommunity.dto.GithubUser;
import hhucommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("c7bd086943ffebd8461e");
        accessTokenDTO.setClient_secret("6c913ed36f68cdfdb179b4dea8f9f89ad5365a3f");
        //用java模拟post请求(httpclient很复杂)
        //用okhttp
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }



}
