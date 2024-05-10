package com.tech.m2.dto;

public class Traffic_LinkVerInfoDto {
    private int chk = 0;
    private String link_id = null;
    private int ver_seq = 0;
    private String dPx_longitude = "null";
    private String dPy_latitude = "null";

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public int getVer_seq() {
        return ver_seq;
    }

    public void setVer_seq(int ver_seq) {
        this.ver_seq = ver_seq;
    }

    public String getdPx_longitude() {
        return dPx_longitude;
    }

    public void setdPx_longitude(String dPx_longitude) {
        this.dPx_longitude = dPx_longitude;
    }

    public String getdPy_latitude() {
        return dPy_latitude;
    }

    public void setdPy_latitude(String dPy_latitude) {
        this.dPy_latitude = dPy_latitude;
    }

    public int getChk() {
        return chk;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }
}
