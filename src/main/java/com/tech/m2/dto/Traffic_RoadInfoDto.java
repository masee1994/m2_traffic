package com.tech.m2.dto;

public class Traffic_RoadInfoDto {
    private int chk = 0;
    private String road_div_cd;
    private String axis_cd;
    private String axis_name;

    public String getRoad_div_cd() {
        return road_div_cd;
    }

    public void setRoad_div_cd(String road_div_cd) {
        this.road_div_cd = road_div_cd;
    }

    public String getAxis_cd() {
        return axis_cd;
    }

    public void setAxis_cd(String axis_cd) {
        this.axis_cd = axis_cd;
    }

    public String getAxis_name() {
        return axis_name;
    }

    public void setAxis_name(String axis_name) {
        this.axis_name = axis_name;
    }

    public int getChk() {
        return chk;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }
}
