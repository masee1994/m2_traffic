package com.tech.m2.dto;

public class Traffic_LinkWithLoadDto {
    private int chk = 0;
    private String axis_cd;
    private String axis_dir;
    private int link_seq;
    private String link_id;

    public String getAxis_cd() {
        return axis_cd;
    }

    public void setAxis_cd(String axis_cd) {
        this.axis_cd = axis_cd;
    }

    public String getAxis_dir() {
        return axis_dir;
    }

    public void setAxis_dir(String axis_dir) {
        this.axis_dir = axis_dir;
    }

    public int getLink_seq() {
        return link_seq;
    }

    public void setLink_seq(int link_seq) {
        this.link_seq = link_seq;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public int getChk() {
        return chk;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }
}
