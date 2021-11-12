package com.amit.poochplayble;


public class HeartRateDataInfo {
    private String heartRateTime;
    private float heartRateNum;

    public HeartRateDataInfo(String heartRateTime, float heartRateNum) {
        this.heartRateTime = heartRateTime;
        this.heartRateNum = heartRateNum;
    }

    public HeartRateDataInfo() {
    }

    public String getHeartRateTime() {
        return this.heartRateTime;
    }

    public void setHeartRateTime(String heartRateTime) {
        this.heartRateTime = heartRateTime;
    }

    public float getHeartRateNum() {
        return this.heartRateNum;
    }

    public void setHeartRateNum(float heartRateNum) {
        this.heartRateNum = heartRateNum;
    }

    public String toString() {
        return "HeartRateDataInfo [heartRateTime=" + this.heartRateTime + ", heartRateNum=" + this.heartRateNum + "]";
    }
}
