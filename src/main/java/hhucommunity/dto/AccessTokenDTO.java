package hhucommunity.dto;

import lombok.Data;

@Data
//encapsulating "AccessToken" into a class:
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
