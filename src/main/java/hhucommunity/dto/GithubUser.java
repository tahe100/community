package hhucommunity.dto;

public class GithubUser {
    private String name;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;
    private Long id;

    private String bio;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }



    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }


}
