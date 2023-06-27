package hhucommunity.controllers;

import hhucommunity.dto.PaginationDTO;
import hhucommunity.mapper.UserMapper;
import hhucommunity.model.CommunityUser;
import hhucommunity.service.TopicService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TopicService topicService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable( name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page" ,defaultValue = "1")Integer page,
                          @RequestParam(name = "size" ,defaultValue = "5")Integer size
                         ){

        CommunityUser user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //快捷键alt + 回车 修复快捷键直接在mapper里创建findByToken方法
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        if(user == null){
            return "redirect:/";
        }

        if(action.equals("topics")){
            model.addAttribute("section","topics");
            model.addAttribute("sectionName","proposed topics");
        } else if (action.equals("responses")) {
            model.addAttribute("section","responses");
            model.addAttribute("sectionName","recent responses");
        }
        //PaginationDTO paginationDTO = topicService.listByUserId(user.getId(),page, size);
        System.out.println("userid is" + user.getId());
        PaginationDTO paginationDTO = topicService.listByUserId(1,page, size);
        //PaginationDTO paginationDTO = topicService.listByUserId(2,page, size);
        System.out.println(paginationDTO.toString());
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
