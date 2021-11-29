package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.os.Message;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SysTimerManager {
    private static TimerList timer_list_first;
    private static Lock lock = new ReentrantLock();

    public SysTimerManager() {
    }

    public static void TimerListReset() {
        timer_list_first = null;
    }

    private static int SysFindTimer(TimerPara tmpTimerPara) {
        int offset = 0;
        if (timer_list_first == null) {
            return 0;
        } else {
            Log.d("moofit", "new timer info:what:" + tmpTimerPara.timerEventPara.what + " arg1:" + tmpTimerPara.timerEventPara.arg1 + " arg2:" + tmpTimerPara.timerEventPara.arg2);

            for(TimerList cur = timer_list_first; cur != null; cur = cur.next) {
                ++offset;
                Log.d("moofit", "find timer seq and info:seq" + offset + "what:" + cur.timerPara.timerEventPara.what + "arg1:" + cur.timerPara.timerEventPara.arg1 + "arg2:" + cur.timerPara.timerEventPara.arg2);
                if (cur.timerPara.timerEventPara.what == tmpTimerPara.timerEventPara.what && cur.timerPara.timerEventPara.arg1 == tmpTimerPara.timerEventPara.arg1 && cur.timerPara.timerEventPara.arg2 == tmpTimerPara.timerEventPara.arg2) {
                    Log.e("moofit--equals", "SysFindTimer: +++++++");
                    return offset;
                }
            }

            return 0;
        }
    }

    public static void SysTimerStart(TimerPara tmpTimerPara, boolean isReload) {
        lock.lock();
        TimerList log_cur = timer_list_first;
        int offset = SysFindTimer(tmpTimerPara);
        Log.d("moofit", "add new timer offset: " + offset);
        TimerList cur;
        if (offset == 0) {
            Log.d("moofit", "add a new timer");
            cur = new TimerList();
            cur.timerPara = tmpTimerPara;
            if (isReload) {
                cur.timerPara.timerTimePara.reloadTimeout = tmpTimerPara.timerTimePara.timeout;
            }

            cur.next = timer_list_first;
            timer_list_first = cur;
        } else {
            Log.d("moofit", "modify timer");
            cur = timer_list_first;

            do {
                cur = cur.next;
                --offset;
            } while(offset != 0);

            if (cur == null) {
                Log.d("moofit-Timer", "Tiemr error");
            }

            cur.timerPara = tmpTimerPara;
        }

        while(log_cur != null) {
            log_cur = log_cur.next;
        }

        lock.unlock();
        Log.e("timer start", "SysTimerStart:out ");
    }

    public static void SysTimerStop(TimerPara tmpTimerPara) {
        lock.lock();

        for(TimerList log_cur = timer_list_first; log_cur != null; log_cur = log_cur.next) {
        }

        int offset = SysFindTimer(tmpTimerPara);
        if (offset == 0) {
            lock.unlock();
        } else {
            TimerList pre = null;
            TimerList cur = timer_list_first;

            do {
                pre = cur;
                cur = cur.next;
                --offset;
            } while(offset != 0);

            if (pre == null) {
                timer_list_first = timer_list_first.next;
            } else {
                pre.next = cur.next;
            }

            lock.unlock();
        }
    }

    public static void SysTimerUpdate(int updatetime) {
        int timercnt = 0;
        lock.lock();
        TimerList pre = null;
        TimerList cur = timer_list_first;
        TimerList log_cur = timer_list_first;

        while(log_cur != null) {
            Log.e("TimerUpdate ", "what: " + log_cur.timerPara.timerEventPara.what + " arg1: " + log_cur.timerPara.timerEventPara.arg1 + " arg2: " + log_cur.timerPara.timerEventPara.arg1);
            log_cur = log_cur.next;
            Log.d("TimerUpdate", "log_cur != null");
        }

        while(true) {
            while(cur != null) {
                ++timercnt;
                Log.d("TimerUpdate", "cur != null");
                if (cur.timerPara.timerTimePara.timeout > updatetime) {
                    TimerTimePara var10000 = cur.timerPara.timerTimePara;
                    var10000.timeout -= updatetime;
                } else {
                    cur.timerPara.timerTimePara.timeout = 0;
                }

                if (cur.timerPara.timerTimePara.timeout == 0) {
                    Log.d("moofit", "timer is end..");
                    Message msg = new Message();
                    msg.what = cur.timerPara.timerEventPara.what;
                    msg.arg1 = cur.timerPara.timerEventPara.arg1;
                    msg.arg2 = cur.timerPara.timerEventPara.arg2;
                    com.amit.poochplayble.SysHanderManager.SendMsg(msg);
                    Log.d("moofit", "timer is end");
                    if (cur.timerPara.timerTimePara.reloadTimeout > 0) {
                        Log.d("moofit", "add a new msg from reload timer");
                        cur.timerPara.timerTimePara.timeout = cur.timerPara.timerTimePara.reloadTimeout;
                        pre = cur;
                    } else {
                        Log.d("moofit", "add a new msg from once timer");
                        if (pre == null) {
                            timer_list_first = cur.next;
                            Log.d("moofit", "timer_list_is null: " + (timer_list_first == null ? 1 : 0));
                        } else {
                            pre.next = cur.next;

                            for(TimerList test_list = timer_list_first; test_list != null; test_list = test_list.next) {
                                Log.d("moofit", "arg2: " + test_list.timerPara.timerEventPara.arg2);
                            }
                        }
                    }

                    cur = cur.next;
                    Log.d("moofit", "cur is null :" + (cur == null ? 1 : 0));
                } else {
                    pre = cur;
                    cur = cur.next;
                }
            }

            lock.unlock();
            return;
        }
    }

    public static class TimerEventPara {
        public int what = 0;
        public int arg1 = 0;
        public int arg2 = 0;

        public TimerEventPara() {
        }
    }

    public static class TimerList {
        public TimerPara timerPara;
        public TimerList next = null;

        public TimerList() {
        }
    }

    public static class TimerPara {
        public TimerEventPara timerEventPara = new TimerEventPara();
        public TimerTimePara timerTimePara = new TimerTimePara();

        public TimerPara() {
        }
    }

    public static class TimerTimePara {
        public int timeout = 0;
        public int reloadTimeout = 0;

        public TimerTimePara() {
        }
    }
}
