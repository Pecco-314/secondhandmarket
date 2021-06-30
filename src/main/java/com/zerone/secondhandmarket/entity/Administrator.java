package com.zerone.secondhandmarket.entity;

public class Administrator {
    private int admin_id;
    private String admin_name;
    private String password;
    public Administrator()
    {

    }
    public Administrator(int admin_id, String admin_name, String password)
    {
        this.admin_id=admin_id;
        this.admin_name=admin_name;
        this.password=password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
