package com.s.z.j.rxjava;


import com.google.gson.Gson;

/**
 * Created by Administrator on 2017-08-29.
 */
public class UserParam {
    private String username;
    private String password;

    public UserParam(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserParam() {
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

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
