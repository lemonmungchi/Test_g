package com.example.k_contest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataPath {
    @SerializedName("header")
    private Header header;
    @SerializedName("body")
    private List<Body> body;

    private Header getHeader(){
        return header;
    }

    private List<Body> getBody(){
        return body;
    }
}

class Header {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultMsg")
    private String resultMsg;
    @SerializedName("numOfRows")
    private int numOfRows;
    @SerializedName("pageNo")
    private int pageNo;
    @SerializedName("totalCount")
    private int totalCount;

    public String getresultMsg(){
        return resultMsg;
    }
    public int getresultCode(){
        return resultCode;
    }
}
class Body{
    @SerializedName("RSTR_ID")
    private int RSTR_ID;
    @SerializedName("RSTR_NM")      //도로명 주소
    private String RSTR_NM;
    @SerializedName("RSTR_LA")     //위도
    private double RSTR_LA;
    @SerializedName("RSTR_LO")     //경도
    private double RSTR_LO;

    public int getRSTR_ID(){
        return RSTR_ID;
    }
    public String getRSTR_NM(){
        return RSTR_NM;
    }
    public double getRSTR_LA(){
        return RSTR_LA;
    }

    public double getRSTR_LO(){
        return RSTR_LO;
    }
}
