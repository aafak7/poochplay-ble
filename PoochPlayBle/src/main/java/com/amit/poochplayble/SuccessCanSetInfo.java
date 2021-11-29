package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class SuccessCanSetInfo {
    public MyCallInterface mCallBack;

    public SuccessCanSetInfo() {
    }

    public void setCallfuc(MyCallInterface mc) {
        this.mCallBack = mc;
    }

    public void call() {
        this.mCallBack.success();
    }
}
