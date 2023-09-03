package com.example.k_contest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.nio.DoubleBuffer;
import java.util.List;

public class RoutePath {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("messge")
    @Expose
    private String messge;

    @SerializedName("currentDateTime")
    @Expose
    private String currentDateTime;

    @SerializedName("route")
    @Expose
    private Route route;

    public int getcode(){
        return code;
    }

    public Route getroute(){
        return route;
    }
}

class Route{
    @SerializedName("summary")
    @Expose
    private List<Trafast> trafast;

    public List<Trafast> getTrafast() {
        return trafast;
    }
}

class Trafast{
    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("path")
    @Expose
    private List<List<Double>> path;

    public Summary getSummary() {
        return summary;
    }

    public List<List<Double>> getPath() {
        return path;
    }
}

class Summary{
    @SerializedName("start")
    @Expose
    private Start start;
    @SerializedName("goal")
    @Expose
    private Goal goal;

    public Goal getGoal() {
        return goal;
    }

    public Start getStart() {
        return start;
    }
}
class Start
{
    @SerializedName("location")
    @Expose
    private List<Double> location;

    public List<Double> getLocation() {
        return location;
    }
}

class Goal
{
    @SerializedName("location")
    @Expose
    private List<Double> location;

    public List<Double> getLocation() {
        return location;
    }
}

