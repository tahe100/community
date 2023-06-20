package hhucommunity.controllers;

import hhucommunity.dto.TopicDTO;
import hhucommunity.mapper.UserMapper;
import hhucommunity.model.CommunityUser;
import hhucommunity.service.TopicService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //快捷键alt + 回车 修复快捷键直接在mapper里创建findByToken方法
                    CommunityUser user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<TopicDTO> topics = topicService.list();
        model.addAttribute("topics",topics);
        return "index";
    }
}
