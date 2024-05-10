package com.tech.m2.dto;

public class Traffic_SpotInfoDto {
    private int chk = 0;
    private String spot_num = "null";
    private String spot_nm = "null";
    private String dPx_longitude = "null";
    private String dPy_latitude = "null";

    public String getSpot_num() {
        return spot_num;
    }

    public String getSpot_nm() {
        return spot_nm;
    }

    public String getdPx_longitude() {
        return dPx_longitude;
    }

    public String getdPy_latitude() {
        return dPy_latitude;
    }

    public void setSpot_num(String spot_num) {
        this.spot_num = spot_num;
    }

    public void setSpot_nm(String spot_nm) {
        this.spot_nm = spot_nm;
    }

    public void setdPx_longitude(String dPx_longitude) {
        this.dPx_longitude = dPx_longitude;
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
