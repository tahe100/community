package hhucommunity.controllers;

import hhucommunity.dto.TopicDTO;
import hhucommunity.model.CommunityUser;
import hhucommunity.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable(name="id") Integer id, Model model, HttpServletRequest request){

        //要topicDTO而不是model里的topic因为topic封装了user
        CommunityUser user = (CommunityUser) request.getSession().getAttribute("user");

        TopicDTO topic = topicService.getById(id);
        //累加阅读数功能
        topicService.incView(id);
        model.addAttribute("topic",topic);
        model.addAttribute("user",user);

        return "topic";

    }



}
