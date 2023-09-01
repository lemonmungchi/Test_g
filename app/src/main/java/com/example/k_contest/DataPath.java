package com.example.k_contest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataPath {
    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("body")
    @Expose
    private List<Body> body;

    public Header getHeader(){
        return header;
    }


    @Override
    public String toString(){
        return "Result [Header = "+header +"Body"+body+"]";
    }

    public List<Body> getBody(){
        return body;
    }
}
class Body {
    @SerializedName("RSTR_ID")
    @Expose
    private int RSTR_ID;
    @SerializedName("RSTR_NM")      //식당이름
    @Expose
    private String RSTR_NM;
    @SerializedName("RSTR_LA")     //위도
    @Expose
    private Double RSTR_LA;
    @SerializedName("RSTR_LO")     //경도
    @Expose
    private Double RSTR_LO;

    @SerializedName(("RSTR_INTRCN_CONT"))       //식당소개
    @Expose
    private String RSTR_INTRCN_CONT;

    public int getRSTR_ID() {
        return RSTR_ID;
    }

    public String getRSTR_NM() {
        return RSTR_NM;
    }

    public Double getRSTR_LA() {
        return RSTR_LA;
    }

    public Double getRSTR_LO() {
        return RSTR_LO;
    }

    public String  getRSTR_INTRCN_CONT() {
        return RSTR_INTRCN_CONT;
    }

    @Override
    public String toString(){
        return "Result [RSTR_ID = "+RSTR_ID +"RSTR_NM"+RSTR_NM+"RSTR_LA"+RSTR_LA+
                "RSTR_LO"+RSTR_LO+"RSTR_INTRCN_CONT="+RSTR_INTRCN_CONT;
    }
}

class Header {
    @SerializedName("resultCode")
    @Expose
    private int resultCode;
    @SerializedName("resultMsg")
    @Expose
    private String resultMsg;
    @SerializedName("numOfRows")
    @Expose
    private int numOfRows;
    @SerializedName("pageNo")
    @Expose
    private int pageNo;
    @SerializedName("totalCount")
    @Expose
    private int totalCount;

    public String getresultMsg() {
        return resultMsg;
    }

    public int getresultCode() {
        return resultCode;
    }

    @Override
    public String toString(){
        return "Result [resultMsg = "+resultMsg +"resultCode="+resultCode+"]";
    }
}

