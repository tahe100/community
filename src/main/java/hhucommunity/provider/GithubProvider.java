package hhucommunity.provider;


import com.alibaba.fastjson.JSON;
import hhucommunity.dto.AccessTokenDTO;
import hhucommunity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component  //spring:By using @Component, when you want to use githbubProvider,
            // you don't have to write GithbubProvider  githbubProvider = new GithbubProvider() anymore.
            //ioc: you can use @Autowired
            /*In this code, the @Component annotation is used to indicate that the GithubProvider class
             is a component or bean managed by the Spring framework's Inversion of Control (IoC) container.
             By using this annotation, you enable the IoC container to automatically instantiate and manage
             instances of the GithubProvider class.
             */
public class GithubProvider {

    //用java模拟post请求(httpclient很复杂)
    //用okhttp
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String [] split = string.split("&");
            String tokenString = split[0];
            String token = tokenString.split("=")[1];
            System.out.println(string);
            System.out.println(token);

            return token;//这个返回的string就是accessToken，然后要做的就是 use the access token to access the API
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();

            try{
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                //用JSON.parseObject(string, GithubUser.class)把string的Json对象自动的解析为java类对象
                System.out.println(string);
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                githubUser.setName(githubUser.getLogin());
                return githubUser;
            }catch(IOException e){

            }
            return null;
    }


}





