package hhucommunity.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    TOPIC_NOT_FOUND(2001,"The question you're looking for isn't available, would you like to try another one?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或是评论进行回复"),
    NO_LOGIN(2003,"you can not write comment please log in first"),
    SYS_ERROR(2004,"Service is too hot, try again later~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"comment not found");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
