package hhucommunity.controllers;

import hhucommunity.dto.TopicDTO;
import hhucommunity.mapper.TopicMapper;
import hhucommunity.model.CommunityUser;
import hhucommunity.model.Topic;
import hhucommunity.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {


    @Autowired
    private TopicService topicService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,Model model){
        TopicDTO topic = topicService.getById(id);
        model.addAttribute("title",topic.getTitle());
        model.addAttribute("description",topic.getDescription());
        model.addAttribute("tag",topic.getTag());
        model.addAttribute("id",id);

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    //前后端分离的项目: 点击提交后在publish页面如果有误 局部刷新publish页面 提示当前提交信息有误。
    //非前后端分离: 点击提交后 要把值传回去请求服务器端去做判断 如果符合就跳转到成功页面 如果不符合就还是回到publish页面
    @PostMapping("/publish")
    public String publish2(@RequestParam(value = "title",required = false)String title,
                           @RequestParam(value = "description",required = false) String description,
                           @RequestParam(value = "tag",required = false)String tag,
                           @RequestParam(value = "id",required = false)Integer id,
                           HttpServletRequest request,
                           Model model){

        //写到前面用于页面回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title.equals("")){
            model.addAttribute("error","The title cannot be empty");
            return "publish";
        }

        if(description == null || description.equals("")){
            model.addAttribute("error","The description cannot be empty");
            return "publish";
        }

        if(tag == null || tag.equals("")){
            model.addAttribute("error","The tag cannot be empty");
            return "publish";
        }

        CommunityUser user = (CommunityUser) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","User not logged in");
            return "publish";
        }

        Topic topic = new Topic();
        topic.setDescription(description);
        topic.setTag(tag);
        topic.setTitle(title);
        topic.setCreator(user.getId());
        topic.setId(id);

        topicService.cteateOrUpdate(topic);
        return "redirect:/";
    }
}
