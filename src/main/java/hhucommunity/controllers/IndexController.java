package hhucommunity.controllers;

import hhucommunity.dto.PaginationDTO;
import hhucommunity.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page" ,defaultValue = "1")Integer page,
                        @RequestParam(name = "size" ,defaultValue = "5")Integer size){

        PaginationDTO pagination = topicService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
