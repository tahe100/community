package hhucommunity.dto;

import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private  String  message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success!");
        return resultDTO;

    }



}
