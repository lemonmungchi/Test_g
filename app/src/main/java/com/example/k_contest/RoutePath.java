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

    public int getCode() {
        return code;
    }

    public Route getRoute() {
        return route;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public String getMessge() {
        return messge;
    }

}

class Route{
    @SerializedName("traoptimal")
    @Expose
    private List<Traoptimal> traoptimal;

    public List<Traoptimal> getTraoptimal() {
        return traoptimal;
    }
}

class Traoptimal{
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

    @SerializedName("distance")
    @Expose
    private int distance;

    public Goal getGoal() {
        return goal;
    }

    public Start getStart() {
        return start;
    }

    public int getDistance() {
        return distance;
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

