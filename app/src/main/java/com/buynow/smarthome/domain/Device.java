package com.buynow.smarthome.domain;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by buynow on 16-6-9.
 */
public class Device {
    //模块类型
    private int type;
    //模块的指令集
    private Map<String,String> cmds;
    //模块的名称
    private String name;
    //模块的ID
    private String id;

    public Device(){}

    //set & get
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, String> getCmds() {
        return cmds;
    }

    public void setCmds(Map<String, String> cmds) {
        this.cmds = cmds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
