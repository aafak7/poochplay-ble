package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.ProtocolTimerManager.TimerPara;

public class BleAppLayerProcess {
    private static final int RT_WAIT_TIMEOUT_PERIOD = 1000;
    private static final int NRT_WAIT_TIMEOUT_PERIOD = 500;
    private static final int CMDID_Pos = 0;
    private static final int VERSION_Pos = 1;
    private static final int PERVIOUS_SEND_PKT_CMDID_Pos = 5;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int DATA_ACK_MSK = 2;
    static AppLayerSaveTransLayerInfo appLayerSaveTransLayerInfo = new AppLayerSaveTransLayerInfo();

    public BleAppLayerProcess() {
    }

    public static void AppLayerTxEventHandle(int event, boolean isNrtDat) {
        Message msg = new Message();
        TimerPara timerPara = new TimerPara();
        switch(event) {
            case 0:
                Log.d("moofit", "TRANS_TX_REGROUP_DAT_SUCCESS ");
                msg.what = 2;
                msg.arg1 = 7;
                msg.arg2 = 9;
                ProtocolHanderManager.SendMsg(msg);
                break;
            case 1:
                Log.d("moofit", "TRANS_TX_TIMEOUT_TIMING_START");
                timerPara.timerEventPara.what = 2;
                timerPara.timerEventPara.arg1 = 7;
                timerPara.timerEventPara.arg2 = 8;
                timerPara.timerTimePara.timeout = isNrtDat ? 500 : 1000;
                ProtocolTimerManager.ProtocolTimerStart(timerPara, false);
                break;
            case 2:
                Log.d("moofit", "TRANS_TX_SEND_DAT_COMPLETE ");
                break;
            case 3:
                Log.d("moofit", "TRANS_TX_SEND_DAT_NOT_COMPLETE ");
                break;
            case 4:
                Log.d("moofit", "TRANS_TX_SEND_DAT_FAIL_EXCEED_RETRY_TIMES ");
                BleHelper.bleOpReConnect();
                break;
            case 5:
                Log.d("moofit", "TRANS_TX_SEND_DAT_SUCCESS_NO_NEED_ACK ");
                msg.what = 2;
                msg.arg1 = 7;
                msg.arg2 = 10;
                ProtocolHanderManager.SendMsg(msg);
                break;
            case 6:
                Log.d("moofit", "TRANS_TX_SEND_DAT_SUCCESS_REPLY_ACK_OK ");
                timerPara.timerEventPara.what = 2;
                timerPara.timerEventPara.arg1 = 7;
                timerPara.timerEventPara.arg2 = 8;
                ProtocolTimerManager.ProtocolTimerStop(timerPara);
                msg.what = 2;
                msg.arg1 = 7;
                msg.arg2 = 10;
                ProtocolHanderManager.SendMsg(msg);
        }

    }

    public static void AppLayerRxDatHandle(int event) {
        Message msg = new Message();
        switch(event) {
            case 8:
                Log.d("moofit", "TRANS_RX_RECIEVE_DATA_AVAILABLE ");
                msg.what = 2;
                msg.arg1 = 5;
                msg.arg2 = 6;
                ProtocolHanderManager.SendMsg(msg);
            default:
        }
    }

    public static class AppLayerSaveTransLayerInfo {
        private boolean is_trans_layer_have_avaliable_data = false;

        public AppLayerSaveTransLayerInfo() {
        }
    }
}
