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
    @SerializedName("RSTR_NM")      //도로명 주소
    @Expose
    private String RSTR_NM;
    @SerializedName("RSTR_LA")     //위도
    @Expose
    private String RSTR_LA;
    @SerializedName("RSTR_LO")     //경도
    @Expose
    private String RSTR_LO;

    public int getRSTR_ID() {
        return RSTR_ID;
    }

    public String getRSTR_NM() {
        return RSTR_NM;
    }

    public String getRSTR_LA() {
        return RSTR_LA;
    }

    public String getRSTR_LO() {
        return RSTR_LO;
    }

    @Override
    public String toString(){
        return "Result [RSTR_ID = "+RSTR_ID +"RSTR_NM"+RSTR_NM+"RSTR_LA"+RSTR_LA+
                "RSTR_LO"+RSTR_LO+"]";
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

