package hhucommunity.advice;

import com.alibaba.fastjson.JSON;
import hhucommunity.dto.ResultDTO;
import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.exception.CustomizeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if(("application/json").equals(contentType)){
            ResultDTO resultDTO;
            //返回json
            if(ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)ex);
            }else{
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;

        }else{
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}

