package hhucommunity.model;

public class HhuUser {//dto是网络中传输中的object，在数据库中我们用model
    //在数据库表里用的是account_id而这里用驼峰表达式
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreat;
    private Long gmtModified;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setGmtCreat(Long gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getToken() {
        return token;
    }

    public Long getGmtCreat() {
        return gmtCreat;
    }

    public Long getGmtModified() {
        return gmtModified;
    }


    @Override
    public String toString() {
        return "HhuUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreat=" + gmtCreat +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
