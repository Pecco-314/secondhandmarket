package com.zerone.secondhandmarket.entity;

public class User {


    private int user_id;
    private String username;
    private String password;
    private String phone_number;
    private String email;
    private UserHead head=UserHead.HEAD0;//用户头像，取值为0,1,2，默认为0

    public User() {

    }

    public User(int user_id, String username, String password, String phone_number, String email, UserHead head) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.email = email;
        this.head = head;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserHead getHead() {
        return head;
    }

    public void setHead(UserHead head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
