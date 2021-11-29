package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.SysTimerManager.TimerPara;

import java.util.Timer;
import java.util.TimerTask;

public class SysHanderManager {
    private static final int REPACK_PEROID = 100;
    private static Timer timer;
    private static TimerTask task;
    private static TimerPara timerPara;
    private static Handler analysis;
    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int err_code = 'ﻮ';
            Log.e("ble_handler", "handleMessage: " + Thread.currentThread().getId());
            if (msg.what == 1) {
                if (msg.arg1 == 3) {
                    Log.e("ble status", "BLE_OP_STATUS_DISCONNECT ");
                    Log.e("ly", "BLE_OP_STATUS_DISCONNECT ");
                    com.amit.poochplayble.BleHelper.bleOpDisconnect();
                    com.amit.poochplayble.BleState.bleStatus = 16;
                    SysSendMsg.StartTimerMsg(1, 4, 500, false);
                } else if (msg.arg1 == 4) {
                    Log.e("ble status", "BLE_OP_STATUS_CLOSE ");
                    Log.e("ly", "BLE_OP_STATUS_CLOSE ");
                    com.amit.poochplayble.BleHelper.bleOpClose();
                    com.amit.poochplayble.BleState.bleStatus = 18;
                    SysSendMsg.StartTimerMsg(1, 1, 2000, false);
                } else if (msg.arg1 == 1) {
                    Log.e("ble status", "BLE_OP_STATUS_CONNECT ");
                    Log.e("ly", "BLE_OP_STATUS_CONNECT ");
                    if (BleConstant.BraceletMac != null) {
                        com.amit.poochplayble.BleHelper.bleOpConnect();
                        com.amit.poochplayble.BleState.bleStatus = 12;
                    }
                } else if (msg.arg1 == 36) {
                    ProtocolTimerManager.TimerListReset();
                    com.amit.poochplayble.BleTransLayer.TransLayerResetPara();
                    BleAppLayerDataProcess.AppLayerResetPara();
                }

                if (msg.arg1 == 66) {
                    Log.e("ly", "BleMarco.SysEvent.BLE_WAIT_FOR_DISCOVERED_NEW");
                    com.amit.poochplayble.BleHelper.WaitTimeForGattServicesDiscoveredNew();
                }

                if (msg.arg1 == 80) {
                    Log.e("ly", "BleMarco.SysEvent.BLE_OPEN_HEART_CHANNEL_EVT");
                    com.amit.poochplayble.BleHelper.bleOpenheartChannel();
                }

                if (com.amit.poochplayble.BleState.bleStatus == 15 || com.amit.poochplayble.BleState.bleStatus == 19) {
                    if (msg.arg1 == 30) {
                        if (com.amit.poochplayble.BleState.bleOldProtocolSyncNrtStatus == 61) {
                            if (com.amit.poochplayble.BleState.bleOldProtocolNrtNotifyOnOffStatus == 52) {
                                Log.e("ly", "需要打开非实时");
                                com.amit.poochplayble.BleHelper.bleOpenNotRealtimeChannel();
                                com.amit.poochplayble.BleState.bleOldProtocolNrtNotifyOnOffStatus = 51;
                            } else {
                                Log.e("ly", "已经打开非实时");
                            }
                        }
                    } else if (msg.arg1 == 32) {
                        if (com.amit.poochplayble.BleState.bleOldProtocolSyncNrtStatus == 61) {
                            if (com.amit.poochplayble.BleState.bleOldProtocolNrtNotifyOnOffStatus == 51) {
                                Log.e("ly", "需要关闭非实时");
                                com.amit.poochplayble.BleHelper.bleCloseNotRealtimeChannel();
                                com.amit.poochplayble.BleState.bleOldProtocolNrtNotifyOnOffStatus = 52;
                            } else {
                                Log.e("ly", "已经关闭非实时");
                            }

                            com.amit.poochplayble.BleHelper.ResetBleOldProtocolSyncNrtProc();
                        }
                    } else if (msg.arg1 == 31) {
                        if (com.amit.poochplayble.BleState.bleOldProtocolRtNotifyOnOffStatus == 52) {
                            Log.e("ly", "需要打开实时");
                            com.amit.poochplayble.BleHelper.bleOpenRealtimeChannel();
                            com.amit.poochplayble.BleState.bleOldProtocolRtNotifyOnOffStatus = 51;
                        } else {
                            Log.e("ly", "已经打开实时");
                        }

                        if (BleConstant.Bracelettype == 2) {
                            Log.e("ly", "打开心率");
                            SysSendMsg.StartTimerMsg(1, 80, 500, false);
                        }
                    }
                }

                if (msg.arg1 == 34) {
                    Log.e("ble", "OLD_PROTOCOL_DECODE_DATA ");
                    Nrtanalysis.setbledate();
                }

                if (msg.arg1 == 1000) {
                    Log.e("xxy", "同步历史数据");
                    Constant.RtNotify = 2;
                    Message mess = new Message();
                    mess.what = 2;
                    mess.arg1 = 14;
                    mess.arg2 = 29;
                    ProtocolHanderManager.SendMsg(mess);
                }
            }

            super.handleMessage(msg);
        }
    };

    public SysHanderManager() {
    }

    public static void setHandler(Handler handler) {
        analysis = handler;
    }

    public static void SendMsg(Message msg) {
        handler.sendMessage(msg);
        Log.e("ble-sendMessage", "SendMsg:Thread: " + Thread.currentThread().getId());
    }

    public static void TimerStart() {
        if (timer == null) {
            task = new TimerTask() {
                public void run() {
                    SysTimerManager.SysTimerUpdate(100);
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
            SysTimerManager.TimerListReset();
        }

    }
}
