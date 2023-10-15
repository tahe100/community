package hhucommunity.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    TOPIC_NOT_FOUND("The question you're looking for isn't available, would you like to try another one?");

    private String message;
    CustomizeErrorCode(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
