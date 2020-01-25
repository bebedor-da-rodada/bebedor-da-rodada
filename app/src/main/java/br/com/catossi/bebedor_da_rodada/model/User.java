package br.com.catossi.bebedor_da_rodada.model;

public class User {

    private String name;
    private String email;
    private String nickname;
    private int id;

    public User() {

    }

    public User(String name, String email, String nickname) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }

    public User(int id, String name, String email, String nickname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
