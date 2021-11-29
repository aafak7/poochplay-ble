package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.ProtocolTimerManager.TimerPara;

import java.util.Timer;
import java.util.TimerTask;

public class ProtocolHanderManager {
    private static final int REPACK_PEROID = 100;
    private static Timer timer;
    private static TimerTask task;
    private static TimerPara timerPara;
    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int err_code = 65262;
            if (msg.what == 2) {
                if (msg.arg1 == 7) {
                    if (msg.arg2 == 9) {
                        Log.d("moofit", "send first");
                        BleTransLayer.TransLayerSendData(0, false);
                    } else if (msg.arg2 == 8) {
                        Log.d("moofit", "send agein");
                        BleTransLayer.TransLayerSendData(0, true);
                    } else if (msg.arg2 == 10) {
                        BleAppLayerDataProcess.AppLayerPushDatToTransLayer();
                    }
                } else if (msg.arg1 == 5) {
                    if (msg.arg2 == 6) {
                        BleAppLayerDataProcess.AppLayerPullDatFromTransLayer();
                    }
                } else if (msg.arg1 == 11) {
                    if (msg.arg2 == 15) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(1);
                    } else if (msg.arg2 == 16) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(2);
                    } else if (msg.arg2 == 17) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(3);
                    } else if (msg.arg2 == 18) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(4);
                    } else if (msg.arg2 == 19) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(5);
                    } else if (msg.arg2 == 20) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(6);
                    } else if (msg.arg2 == 21) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(7);
                    } else if (msg.arg2 == 22) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(8);
                    } else if (msg.arg2 == 23) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(9);
                    } else if (msg.arg2 == 24) {
                        err_code = BleEncodeRtData.EncodePublicCtrlData(10);
                    }

                    Log.d("moofit-HANDDERMANAGE", "errcode = " + err_code);
                    if (err_code != 65262) {
                        if (err_code == 0) {
                            Log.d("moofit-HANDDERMANAGE", "encode succ= ");
                            BleAppLayerDataProcess.AppLayerRtEncodeEnd();
                        } else if (err_code != -3) {
                            ProtocolHanderManager.timerPara = new TimerPara();
                            Log.d("moofit-HANDDERMANAGE", "encode fail= ");
                            ProtocolHanderManager.timerPara.timerEventPara.what = 2;
                            ProtocolHanderManager.timerPara.timerEventPara.arg1 = msg.arg1;
                            ProtocolHanderManager.timerPara.timerEventPara.arg2 = msg.arg2;
                            ProtocolHanderManager.timerPara.timerTimePara.timeout = 100;
                            ProtocolTimerManager.ProtocolTimerStart(ProtocolHanderManager.timerPara, false);
                        }
                    }
                } else if (msg.arg1 == 12) {
                    if (msg.arg2 == 25) {
                        err_code = BleEncodeRtData.EncodePublicGetData(1);
                    }

                    if (msg.arg2 == 26) {
                        err_code = BleEncodeRtData.EncodePublicGetData(2);
                    }

                    Log.d("moofit-HANDDERMANAGE", "errcode = " + err_code);
                    if (err_code != 65262) {
                        if (err_code == 0) {
                            Log.d("moofit-HANDDERMANAGE", "encode succ== ");
                            BleAppLayerDataProcess.AppLayerRtEncodeEnd();
                        } else if (err_code != -3) {
                            ProtocolHanderManager.timerPara = new TimerPara();
                            Log.d("moofit-HANDDERMANAGE", "encode fail== ");
                            ProtocolHanderManager.timerPara.timerEventPara.what = 2;
                            ProtocolHanderManager.timerPara.timerEventPara.arg1 = msg.arg1;
                            ProtocolHanderManager.timerPara.timerEventPara.arg2 = msg.arg2;
                            ProtocolHanderManager.timerPara.timerTimePara.timeout = 100;
                            ProtocolTimerManager.ProtocolTimerStart(ProtocolHanderManager.timerPara, false);
                        }
                    }
                } else if (msg.arg1 == 13) {
                    if (msg.arg2 == 27) {
                        err_code = BleEncodeRtData.EncodePublicAndroidInfoData(1);
                    } else if (msg.arg2 == 28) {
                        err_code = BleEncodeRtData.EncodePublicAndroidInfoData(2);
                    }

                    Log.d("moofit-HANDDERMANAGE", "errcode = " + err_code);
                    if (err_code != 65262) {
                        if (err_code == 0) {
                            Log.d("moofit-HANDDERMANAGE", "encode succ ");
                            BleAppLayerDataProcess.AppLayerRtEncodeEnd();
                        } else if (err_code != -3) {
                            ProtocolHanderManager.timerPara = new TimerPara();
                            Log.d("moofit-HANDDERMANAGE", "encode fail ");
                            ProtocolHanderManager.timerPara.timerEventPara.what = 2;
                            ProtocolHanderManager.timerPara.timerEventPara.arg1 = msg.arg1;
                            ProtocolHanderManager.timerPara.timerEventPara.arg2 = msg.arg2;
                            ProtocolHanderManager.timerPara.timerTimePara.timeout = 100;
                            ProtocolTimerManager.ProtocolTimerStart(ProtocolHanderManager.timerPara, false);
                        }
                    }
                } else if (msg.arg1 == 14) {
                    if (msg.arg2 == 29) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(0);
                    } else if (msg.arg2 == 30) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(1);
                    } else if (msg.arg2 == 31) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(2);
                    } else if (msg.arg2 == 32) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(3);
                    } else if (msg.arg2 == 33) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(4);
                    } else if (msg.arg2 == 34) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(5);
                    } else if (msg.arg2 == 35) {
                        err_code = BleEncodeNrtData.EncodePublicGetNrtData(6);
                    }

                    if (err_code != 65262) {
                        if (err_code == 0) {
                            BleAppLayerDataProcess.AppLayerNrtEncodeEnd();
                        } else if (err_code != -3) {
                            ProtocolHanderManager.timerPara = new TimerPara();
                            Log.e("ble", "nrt data timer start ");
                            ProtocolHanderManager.timerPara.timerEventPara.what = 2;
                            ProtocolHanderManager.timerPara.timerEventPara.arg1 = msg.arg1;
                            ProtocolHanderManager.timerPara.timerEventPara.arg2 = msg.arg2;
                            ProtocolHanderManager.timerPara.timerTimePara.timeout = 100;
                            ProtocolTimerManager.ProtocolTimerStart(ProtocolHanderManager.timerPara, false);
                        }
                    }
                }
            }

            super.handleMessage(msg);
        }
    };

    public ProtocolHanderManager() {
    }

    public static void SendMsg(Message msg) {
        handler.sendMessage(msg);
        Log.e("ble-sendMessage", "SendMsg:Thread: " + Thread.currentThread().getId());
    }

    public static void TimerStart() {
        if (timer == null) {
            task = new TimerTask() {
                public void run() {
                    ProtocolTimerManager.ProcotolTimerUpdate(100);
                }
            };
            timer = new Timer();
            timer.schedule(task, 1000L, 100L);
        }

    }

    public static void TimerCancel() {
        Log.d("moofit", " TimerCancel");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            task = null;
            ProtocolTimerManager.TimerListReset();
        }

    }
}
