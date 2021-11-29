package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.Serializable;

public class AlarmPlanData implements Serializable {
    int numbers;
    String planName;
    String remindTime;
    String remindUtc;
    int openStatus;
    int[] day;

    public AlarmPlanData() {
    }

    public String getPlanName() {
        return this.planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getRemindTime() {
        return this.remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public int getOpenStatus() {
        return this.openStatus;
    }

    public void setOpenStatus(int openStatus) {
        this.openStatus = openStatus;
    }

    public int[] getDay() {
        return this.day;
    }

    public void setDay(int[] day) {
        this.day = day;
    }

    public String getRemindUtc() {
        return this.remindUtc;
    }

    public void setRemindUtc(String remindUtc) {
        this.remindUtc = remindUtc;
    }

    public int getNumbers() {
        return this.numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
