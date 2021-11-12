package com.amit.poochplayble;


public class StepData {
    public String steptime;
    public int stepdata;
    public int caloriedata;
    public String marktime;
    public int utctime;

    public StepData() {
    }

    public int getUtctime() {
        return this.utctime;
    }

    public void setUtctime(int utctime) {
        this.utctime = utctime;
    }

    public String getMarktime() {
        return this.marktime;
    }

    public void setMarktime(String marktime) {
        this.marktime = marktime;
    }

    public String getSteptime() {
        return this.steptime;
    }

    public void setSteptime(String steptime) {
        this.steptime = steptime;
    }

    public int getStepdata() {
        return this.stepdata;
    }

    public void setStepdata(int stepdata) {
        this.stepdata = stepdata;
    }

    public int getCaloriedata() {
        return this.caloriedata;
    }

    public void setCaloriedata(int caloriedata) {
        this.caloriedata = caloriedata;
    }
}
