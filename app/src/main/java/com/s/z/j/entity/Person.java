package com.s.z.j.entity;

/**
 * Created by szj on 2015/8/10.
 */
public class Person {
    private String appid;
    private String codebase;
    private String version;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCodebase() {
        return codebase;
    }

    public void setCodebase(String codebase) {
        this.codebase = codebase;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Person() {
    }
}
