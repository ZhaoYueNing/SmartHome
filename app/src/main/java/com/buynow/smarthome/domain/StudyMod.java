package com.buynow.smarthome.domain;

/**
 * Created by Zhao on 2016/7/17.
 *
 */
public class StudyMod  {
    private String name;
    private int id;

    public StudyMod(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
