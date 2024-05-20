package com.tech.m2.dto;

public class Traffic_AccInfoDto {

    private int chk = 0;
    private String acc_id = "null";
    private String occr_date = "null";
    private String occr_time = "null";
    private String exp_clr_date = "null";
    private String exp_clr_time = "null";
    private String acc_type = "null";
    private String acc_dtype = "null";
    private String link_id = "null";
    private String dPx_longitude = "null";
    private String dPy_latitude = "null";
    private String acc_info = "null";
    private String acc_road_code = "null";

    public String getAcc_road_code() {
        return acc_road_code;
    }

    public void setAcc_road_code(String acc_road_code) {
        this.acc_road_code = acc_road_code;
    }

    public String getAcc_info() {
        return acc_info;
    }

    public void setAcc_info(String acc_info) {
        this.acc_info = acc_info;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getOccr_date() {
        if (occr_date != null && occr_date.length() == 8) {
            return occr_date.substring(0, 4) + "-" + occr_date.substring(4, 6) + "-" + occr_date.substring(6, 8);
        }
        return occr_date;
    }

    public void setOccr_date(String occr_date) {
        this.occr_date = occr_date;
    }

    public String getOccr_time() {
        if (occr_time != null && occr_time.length() == 6) {
            return occr_time.substring(0, 2) + ":" + occr_time.substring(2, 4);
        }else if (occr_time != null && occr_time.length() == 4) {
            return occr_time.substring(0, 2) + ":" + occr_time.substring(2, 4);
        }
        return occr_time;
    }

    public void setOccr_time(String occr_time) {
        this.occr_time = occr_time;
    }

    public String getExp_clr_date() {
        if (exp_clr_date != null && exp_clr_date.length() == 8) {
            return exp_clr_date.substring(0, 4) + "-" + exp_clr_date.substring(4, 6) + "-" + exp_clr_date.substring(6, 8);
        }
        return exp_clr_date;
    }

    public void setExp_clr_date(String exp_clr_date) {

        this.exp_clr_date = exp_clr_date;
    }

    public String getExp_clr_time() {
        if (exp_clr_time != null && exp_clr_time.length() == 6) {
            return exp_clr_time.substring(0, 2) + ":" + exp_clr_time.substring(2, 4);
        }else if (exp_clr_time != null && exp_clr_time.length() == 4) {
            return exp_clr_time.substring(0, 2) + ":" + exp_clr_time.substring(2, 4);
        }
        return exp_clr_time;
    }

    public void setExp_clr_time(String exp_clr_time) {
        this.exp_clr_time = exp_clr_time;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String acc_type) {
        this.acc_type = acc_type;
    }

    public String getAcc_dtype() {
        return acc_dtype;
    }

    public void setAcc_dtype(String acc_dtype) {
        this.acc_dtype = acc_dtype;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getdPx_longitude() {
        return dPx_longitude;
    }

    public String getdPy_latitude() {
        return dPy_latitude;
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
