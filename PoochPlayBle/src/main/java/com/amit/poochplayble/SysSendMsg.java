package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.os.Message;

import com.amit.poochplayble.SysTimerManager.TimerPara;

public class SysSendMsg {
    public SysSendMsg() {
    }

    public static void StartTimerMsg(int what, int event, int timeout, boolean isRepeat) {
        TimerPara timerPara = new TimerPara();
        timerPara.timerEventPara.what = what;
        timerPara.timerEventPara.arg1 = event;
        timerPara.timerTimePara.timeout = timeout;
        SysTimerManager.SysTimerStart(timerPara, isRepeat);
    }

    public static void StopTimerMsg(int what, int event) {
        TimerPara timerPara = new TimerPara();
        timerPara.timerEventPara.what = what;
        timerPara.timerEventPara.arg1 = event;
        SysTimerManager.SysTimerStop(timerPara);
    }

    public static void SendNomalMsg(int what, int arg1) {
        Message message = new Message();
        message.what = what;
        message.arg1 = arg1;
        com.amit.poochplayble.SysHanderManager.SendMsg(message);
    }
}
