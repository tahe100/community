package hhucommunity.controllers;


import hhucommunity.dto.CommentDTO;
import hhucommunity.dto.ResultDTO;
import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.model.Comment;
import hhucommunity.model.CommunityUser;
import hhucommunity.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){

        CommunityUser user =(CommunityUser)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment( );
        //我找到错误的地方了就是这里commentDTO.getParentID()居然为null
        System.out.println(commentDTO.getParentID()+"hhhhhhhhhhhhhhhhhhhhhhhhh");
        //commentDTO.getContent()这个值拿到了
        System.out.println(commentDTO.getContent()+"Content");
        //commentDTO.getType()这个值拿到了
        System.out.println(commentDTO.getType()+"type");
        comment.setParentId(commentDTO.getParentID());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1);
        comment.setLikeCount(0L);

        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
