package hhucommunity.controllers;

import hhucommunity.dto.CommentDTO;
import hhucommunity.dto.CommentInTopicDTO;
import hhucommunity.dto.TopicDTO;
import hhucommunity.model.CommunityUser;
import hhucommunity.service.CommentService;
import hhucommunity.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable(name="id") Integer id, Model model, HttpServletRequest request){

        //要topicDTO而不是model里的topic因为topic封装了user
        CommunityUser user = (CommunityUser) request.getSession().getAttribute("user");

        TopicDTO topic = topicService.getById(id);

        List<CommentInTopicDTO> comments = commentService.listByTopicId(id);


        //累加阅读数功能
        topicService.incView(id);
        model.addAttribute("topic",topic);
        model.addAttribute("user",user);
        model.addAttribute("comments", comments);

        return "topic";

    }



}
