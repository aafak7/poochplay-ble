package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.BleTransLayer.FifoBuf;


public class BleAppLayerDataProcess {
    private static final int PACKET_HEADER_LEN = 2;
    private static final int CMDID_Pos = 0;
    private static final int VERSION_Pos = 1;
    private static final int ACK_Pos = 3;
    private static final int PKT_SEQ_LEN = 2;
    private static final int NO_NEED_ACK_MSK = 0;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int DATA_ACK_MSK = 2;
    private static final int ACK_MSK = 3;
    private static final int KEY_Pos = 2;
    private static final int App_LAYER_RX_BUF_LEN = 100;
    private static final int App_LAYER_TX_BUF_LEN = 100;
    private static final int PACK_KEY_MAX = 16;
    private static final int APP_PKT_SEQ_MAX = 65535;
    static int sendPktSeq;
    static AlreadyPackCnt alreadyPackCnt = new AlreadyPackCnt();
    static AppLayerRxInfo appLayerRxInfo = new AppLayerRxInfo();
    static AppLayerTxInfo appLayerTxInfo = new AppLayerTxInfo();
    static BleTransLayer.TransLayerTxCpmrInfo cmprRtInfo = new BleTransLayer.TransLayerTxCpmrInfo();
    static BleTransLayer.TransLayerTxCpmrInfo cmprNrtInfo = new BleTransLayer.TransLayerTxCpmrInfo();

    public BleAppLayerDataProcess() {
    }

    public static void AppLayerResetPara() {
        AppLayerResetSendPktSeq();
        AppLayerResetRtTxBufInfo();
        AppLayerResetNrtTxBufInfo();
        AppLayerResetRxBufInfo();
    }

    private static void AppLayerResetSendPktSeq() {
        sendPktSeq = 1;
    }

    private static void AppLayerResetRtCmprInfo() {
        cmprRtInfo.isNeedAck = false;
        cmprRtInfo.isNrtData = false;
        cmprRtInfo.appLayerPktSeq = 0;
    }

    private static void AppLayerResetRtTxBufInfo() {
        appLayerTxInfo.fifoRtBuf.isAvailable = false;
        appLayerTxInfo.fifoRtBuf.readPos = 0;
        appLayerTxInfo.fifoRtBuf.writePos = 0;
        AppLayerResetRtCmprInfo();
    }

    private static void AppLayerResetNrtCmprInfo() {
        cmprNrtInfo.isNeedAck = false;
        cmprNrtInfo.isNrtData = false;
        cmprNrtInfo.appLayerPktSeq = 0;
    }

    private static void AppLayerResetNrtTxBufInfo() {
        appLayerTxInfo.fifoNrtBuf.isAvailable = false;
        appLayerTxInfo.fifoNrtBuf.writePos = 0;
        appLayerTxInfo.fifoNrtBuf.readPos = 0;
        AppLayerResetNrtCmprInfo();
    }

    public static int AppLayerRtEncodeDat(byte[] src, int len) {
        if (BleState.bleStatus == 18) {
            return -3;
        } else {
            Log.d("moofit", " 需要判断通道是否打开");
            Log.d("moofit", "AppLayerRtEncodeDat,len=" + len + "cmdid=" + src[0]);
            Log.d("moofit", "cmdid: " + appLayerTxInfo.aPPLayerRtTxBuf[0] + "len:" + appLayerTxInfo.fifoRtBuf.writePos);
            FifoBuf var10000;
            if (appLayerTxInfo.fifoRtBuf.writePos != 0) {
                if (appLayerTxInfo.aPPLayerRtTxBuf[2] != src[0]) {
                    return -1;
                }

                if (100 - appLayerTxInfo.fifoRtBuf.writePos < len - 2) {
                    return -2;
                }

                System.arraycopy(src, 2, appLayerTxInfo.aPPLayerRtTxBuf, appLayerTxInfo.fifoRtBuf.writePos, len - 2);
                var10000 = appLayerTxInfo.fifoRtBuf;
                var10000.writePos += len - 2;
            } else {
                if (len >= 100) {
                    return -2;
                }

                appLayerTxInfo.fifoRtBuf.writePos = 2;
                System.arraycopy(src, 0, appLayerTxInfo.aPPLayerRtTxBuf, appLayerTxInfo.fifoRtBuf.writePos, len);
                var10000 = appLayerTxInfo.fifoRtBuf;
                var10000.writePos += len;
            }

            if (!cmprRtInfo.isNeedAck && (src[3] & 3) != 0) {
                cmprRtInfo.isNeedAck = true;
            }

            return 0;
        }
    }

    public static void AppLayerRtEncodeEnd() {
        Log.d("moofit", "AppLayerEncodeEnd,LEN=" + appLayerTxInfo.fifoRtBuf.writePos);
        if (appLayerTxInfo.fifoRtBuf.writePos != 0) {
            System.out.println("");

            for(int i = 0; i < appLayerTxInfo.fifoRtBuf.writePos; ++i) {
                Log.d("moofit", "data=" + appLayerTxInfo.aPPLayerRtTxBuf[i]);
            }

            appLayerTxInfo.fifoRtBuf.isAvailable = true;
            AppLayerPushDatToTransLayer();
        }

    }

    public static int AppLayerNrtEncodeDat(byte[] src, int len) {
        if (BleState.bleStatus == 18) {
            return -3;
        } else {
            Log.d("moofit", " 需要判断通道是否打开");
            Log.d("moofit", "AppLayerNrtEncodeDat,len=" + len + "cmdid=" + src[0]);
            Log.d("moofit", "cmdid: " + appLayerTxInfo.APPLayerNrtTxBuf[0] + "len:" + appLayerTxInfo.fifoNrtBuf.writePos);
            FifoBuf var10000;
            if (appLayerTxInfo.fifoNrtBuf.writePos != 0) {
                if (appLayerTxInfo.APPLayerNrtTxBuf[2] != src[0]) {
                    return -1;
                }

                if (100 - appLayerTxInfo.fifoNrtBuf.writePos < len - 2) {
                    return -2;
                }

                System.arraycopy(src, 2, appLayerTxInfo.APPLayerNrtTxBuf, appLayerTxInfo.fifoNrtBuf.writePos, len - 2);
                var10000 = appLayerTxInfo.fifoNrtBuf;
                var10000.writePos += len - 2;
            } else {
                if (len >= 100) {
                    return -2;
                }

                appLayerTxInfo.fifoNrtBuf.writePos = 2;
                System.arraycopy(src, 0, appLayerTxInfo.APPLayerNrtTxBuf, appLayerTxInfo.fifoNrtBuf.writePos, len);
                var10000 = appLayerTxInfo.fifoNrtBuf;
                var10000.writePos += len;
            }

            if (!cmprNrtInfo.isNeedAck && (src[3] & 3) != 0) {
                cmprNrtInfo.isNeedAck = true;
            }

            Log.e("ble", "NRT appBuf ENCODE SUCC ");
            return 0;
        }
    }

    public static void AppLayerNrtEncodeEnd() {
        Log.d("moofit", "AppLayerEncodeEnd,LEN=" + appLayerTxInfo.fifoNrtBuf.writePos);
        if (appLayerTxInfo.fifoNrtBuf.writePos != 0) {
            System.out.println("");

            for(int i = 0; i < appLayerTxInfo.fifoNrtBuf.writePos; ++i) {
                Log.d("ble", "data=" + appLayerTxInfo.APPLayerNrtTxBuf[i]);
            }

            appLayerTxInfo.fifoNrtBuf.isAvailable = true;
            AppLayerPushDatToTransLayer();
        }

    }

    public static void AppLayerPushDatToTransLayer() {
        Log.d("moofit", "AppLayerPushDatToTransLayer");
        if (appLayerTxInfo.fifoRtBuf.isAvailable || appLayerTxInfo.fifoNrtBuf.isAvailable) {
            sendPktSeq = (sendPktSeq + 1 & '\uffff') == 0 ? 1 : sendPktSeq + 1;
            int err_code;
            if (appLayerTxInfo.fifoRtBuf.isAvailable) {
                appLayerTxInfo.aPPLayerRtTxBuf[0] = (byte)(sendPktSeq >> 8);
                appLayerTxInfo.aPPLayerRtTxBuf[1] = (byte)sendPktSeq;
                cmprRtInfo.isNrtData = false;
                cmprRtInfo.appLayerPktSeq = sendPktSeq;
                err_code = BleTransLayer.TransLayerRegroupAppLayerData(appLayerTxInfo.aPPLayerRtTxBuf, appLayerTxInfo.fifoRtBuf.writePos, cmprRtInfo);
                if (err_code == -2) {
                    Log.d("moofit", "AppLayerPushDatToTransLayer err ");
                } else if (err_code == 0) {
                    AppLayerResetRtTxBufInfo();
                } else {
                    sendPktSeq = (sendPktSeq - 1 & '\uffff') == 0 ? '\uffff' : sendPktSeq - 1;
                }
            } else {
                appLayerTxInfo.APPLayerNrtTxBuf[0] = (byte)(sendPktSeq >> 8);
                appLayerTxInfo.APPLayerNrtTxBuf[1] = (byte)sendPktSeq;
                cmprNrtInfo.isNrtData = true;
                cmprNrtInfo.appLayerPktSeq = sendPktSeq;
                err_code = BleTransLayer.TransLayerRegroupAppLayerData(appLayerTxInfo.APPLayerNrtTxBuf, appLayerTxInfo.fifoNrtBuf.writePos, cmprNrtInfo);
                Log.e("ble", "AppLayerPushDatToTransLayer:nrt data errcode" + err_code);
                if (err_code == -2) {
                    Log.d("moofit", "AppLayerPushDatToTransLayer err ");
                } else if (err_code == 0) {
                    AppLayerResetNrtTxBufInfo();
                } else {
                    sendPktSeq = (sendPktSeq - 1 & '\uffff') == 0 ? '\uffff' : sendPktSeq - 1;
                }
            }

        }
    }

    private static void AppLayerResetRxBufInfo() {
        appLayerRxInfo.fifoBuf.readPos = 0;
        appLayerRxInfo.fifoBuf.writePos = 0;
        appLayerRxInfo.fifoBuf.isAvailable = false;
    }

    public static void AppLayerPullDatFromTransLayer() {
        Message msg = new Message();
        BleTransLayer.TransLayerPullDat(appLayerRxInfo);
        Log.d("moofit", "AppLayerPullDatFromTransLayer" + appLayerRxInfo.fifoBuf.isAvailable);
        if (appLayerRxInfo.fifoBuf.isAvailable) {
            BleDecodeData.DataDecode(appLayerRxInfo);
            AppLayerResetRxBufInfo();
            msg.what = 2;
            msg.arg1 = 5;
            msg.arg2 = 6;
            ProtocolHanderManager.SendMsg(msg);
        }

    }

    static class AlreadyPackCnt {
        private int RtPackCnt = 0;
        private int NrtPackCnt = 0;

        AlreadyPackCnt() {
        }
    }

    static class AppLayerRxInfo {
        FifoBuf fifoBuf = new FifoBuf();
        public int[] appLayerRxBuf = new int[100];

        AppLayerRxInfo() {
        }
    }

    static class AppLayerTxInfo {
        FifoBuf fifoRtBuf = new FifoBuf();
        private byte[] aPPLayerRtTxBuf = new byte[100];
        FifoBuf fifoNrtBuf = new FifoBuf();
        private byte[] APPLayerNrtTxBuf = new byte[100];

        AppLayerTxInfo() {
        }
    }
}
