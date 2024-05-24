package com.tech.m2.dto;

public class BusInfoDto {
    private String NODE_ID;
    private String ARS_ID;
    private String name;
    private String dPx_longitude;
    private String dPy_latitude;
    private String type;

    public String getNODE_ID() {
        return NODE_ID;
    }

    public void setNODE_ID(String NODE_ID) {
        this.NODE_ID = NODE_ID;
    }

    public String getARS_ID() {
        return ARS_ID;
    }

    public void setARS_ID(String ARS_ID) {
        this.ARS_ID = ARS_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
