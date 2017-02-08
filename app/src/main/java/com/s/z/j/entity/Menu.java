package com.s.z.j.entity;

/**
 * 主菜单选项
 * Created by Administrator on 2017-02-08.
 */
public class Menu  {
    private int id;
    private String name;

    public Menu() {
    }

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
