package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.util.Log;

import com.amit.poochplayble.BleAppLayerDataProcess.AppLayerRxInfo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BleTransLayer {
    private static BleService mBleService;
    private static final int MAX_SERVICE_DAT_LEN = 20;
    private static final int TRANS_LAYER_HEADER = 255;
    private static final int MULTIPLE_PKT = 128;
    private static final int CONTINUE_PKT = 0;
    private static final int END_PKT = 64;
    private static final int MAX_SEQ_NUM = 16;
    private static final int MAX_ONE_PKT_DAT_LEN = 16;
    private static final int MAX_RETRY_TIMES = 5;
    private static final int MAX_NRT_DATA_RETRY_TIMES = 20;
    private static final int MAX_RT_DATA_RETRY_TIMES = 20;
    private static final int SIGLE_PKT_MSK = 0;
    private static final int TRANS_LAYER_HEADER_Pos = 0;
    private static final int MULTIPLE_PKT_SEQ_MSK = 15;
    private static final int TRANS_LAYER_PKT_HEAD_TAIL_LEN = 4;
    private static final int TRANS_LAYER_PKT_VALID_DATA_Pos = 3;
    private static final int TRANS_LAYER_PKT_MSK_Pos = 1;
    private static final int TRANS_ACK_PKT_MSK = 32;
    private static final int ACK_DATA_LEN = 3;
    private static final int ACK_APP_LAYER_SEQ_Pos = 3;
    private static final int ACK_ERRCODE_MSK_Pos = 6;
    private static final int ACK_TRANS_LAYER_TOTAL_BYTE_Pos = 7;
    private static final int APP_LAYER_PKT_SEQ_Pos = 0;
    private static final int APP_LAYER_PKT_SEQ_LEN = 2;
    private static final int TRANS_LAYER_PKT_LEN_Pos = 2;
    private static final int TRANS_LAYER_PKT_APP_PKT_SEQ_Pos = 3;
    private static final int TRANS_LAYER_RX_BUF_LEN = 140;
    private static final int TRANS_LAYER_TX_BUF_LEN = 100;
    public static final int TRANS_TX_REGROUP_DAT_SUCCESS = 0;
    public static final int TRANS_TX_TIMEOUT_TIMING_START = 1;
    public static final int TRANS_TX_SEND_DAT_COMPLETE = 2;
    public static final int TRANS_TX_SEND_DAT_NOT_COMPLETE = 3;
    public static final int TRANS_TX_SEND_DAT_FAIL_EXCEED_RETRY_TIMES = 4;
    public static final int TRANS_TX_SEND_DAT_SUCCESS_NO_NEED_ACK = 5;
    public static final int TRANS_TX_SEND_DAT_SUCCESS_REPLY_ACK_OK = 6;
    public static final int TRANS_RX_ACK_CONFIRM = 7;
    public static final int TRANS_RX_RECIEVE_DATA_AVAILABLE = 8;
    private static int maxRetryTiems;
    private static int prevTransLayerSeq = 0;
    private static int lastRecvAppLayerPktSeq;
    private static int curRecvAppLayerPktSeq;
    public static TransLayerRxInfo transLayerrxInfo = new TransLayerRxInfo();
    private static TransLayerTxInfo transLayertxInfo = new TransLayerTxInfo();
    private static TransLayerTxCpmrInfo transLayerTxCmprInfo = new TransLayerTxCpmrInfo();
    private static Lock lock = new ReentrantLock();

    public BleTransLayer(BleService mBleService) {
        TransLayerResetPara();
        ProtocolTimerManager.TimerListReset();
        BleAppLayerDataProcess.AppLayerResetPara();
        Log.d("moofit", "AppLayerResetTxBufInfo");
    }

    private static void TransLayerResetRxInfoPara() {
        Log.d("moofit", "TransLayerResetRxInfoPara");
        transLayerrxInfo.fifoBuf.isAvailable = false;
        transLayerrxInfo.fifoBuf.writePos = 0;
        transLayerrxInfo.fifoBuf.readPos = 0;
        transLayerrxInfo.fifoBuf.fifoUsageLen = 0;
    }

    public static void TranslayerRecievePkt(byte[] bytes) {
        Log.e("ly", "++++");
        int pktLen = bytes.length;
        int[] tmpPkt = new int[20];
        Log.d("moofit", "TranslayerRecievePkt");

        for(int i = 0; i < pktLen; ++i) {
            tmpPkt[i] = bytes[i];
            if (tmpPkt[i] < 0) {
                tmpPkt[i] += 256;
            }
        }

        if (transLayerrxInfo.fifoBuf.fifoUsageLen < 140) {
            if (transLayerrxInfo.fifoBuf.writePos >= 140) {
                transLayerrxInfo.fifoBuf.writePos = 0;
            }

            System.arraycopy(tmpPkt, 0, TransLayerRxInfo.tranLayerRxBuf, transLayerrxInfo.fifoBuf.writePos, pktLen);
            FifoBuf var10000 = transLayerrxInfo.fifoBuf;
            var10000.writePos += 20;
            var10000 = transLayerrxInfo.fifoBuf;
            var10000.fifoUsageLen += 20;
            if (!transLayerrxInfo.fifoBuf.isAvailable) {
                transLayerrxInfo.fifoBuf.isAvailable = true;
                BleAppLayerProcess.AppLayerRxDatHandle(8);
            }

        }
    }

    public static void TransLayerPullDat(AppLayerRxInfo appLayerRxFifo) {
        boolean isPktEnd = false;
        int[] tmpBuf = new int[20];

        Log.d("moofit", "TransLayerPullDat: ");

        while(transLayerrxInfo.fifoBuf.isAvailable) {
            if (transLayerrxInfo.fifoBuf.readPos >= 140) {
                transLayerrxInfo.fifoBuf.readPos = 0;
            }

            System.arraycopy(TransLayerRxInfo.tranLayerRxBuf, transLayerrxInfo.fifoBuf.readPos, tmpBuf, 0, 20);
            FifoBuf var10000 = transLayerrxInfo.fifoBuf;
            var10000.readPos += 20;
            var10000 = transLayerrxInfo.fifoBuf;
            var10000.fifoUsageLen -= 20;
            if (transLayerrxInfo.fifoBuf.fifoUsageLen == 0) {
                transLayerrxInfo.fifoBuf.isAvailable = false;
            }

            if (tmpBuf[0] == 255) {
                int tmpLen = tmpBuf[2];

                int curTransLayerSeq;
                for(curTransLayerSeq = 0; curTransLayerSeq < tmpLen; ++curTransLayerSeq) {
                    Log.d("moofit-TransLayerDebug", " " + tmpBuf[curTransLayerSeq]);
                }

                if (tmpBuf[tmpLen - 1] == Util.get_checksum(0, tmpBuf, 0, tmpLen - 1)) {
                    if ((tmpBuf[1] & 32) == 32) {
                        if (tmpLen == 6) {
                            curTransLayerSeq = (tmpBuf[3] << 8) + tmpBuf[4];
                            Log.d("moofit-TransLayerDebug", "ack decode seq=" + curTransLayerSeq + "   app_seq:" + transLayerTxCmprInfo.appLayerPktSeq);
                            if (transLayerTxCmprInfo.appLayerPktSeq == curTransLayerSeq) {
                                if (transLayerTxCmprInfo.isNeedAck) {
                                    BleAppLayerProcess.AppLayerTxEventHandle(6, transLayerTxCmprInfo.isNrtData);
                                }

                                TransLayerResetTxInfoPara();
                            }
                        }
                    } else {
                        if ((tmpBuf[1] & 128) == 128) {
                            int cpyOffset = 0;
                            curTransLayerSeq = tmpBuf[1] & 15;
                            if (tmpLen != 20 && (tmpBuf[1] & 64) != 64) {
                                continue;
                            }

                            if (curTransLayerSeq == 0) {
                                cpyOffset = 2;
                                appLayerRxFifo.fifoBuf.writePos = 0;
                                prevTransLayerSeq = 0;
                                curRecvAppLayerPktSeq = (tmpBuf[3] << 8) + tmpBuf[4];
                            } else if (prevTransLayerSeq + 1 != curTransLayerSeq) {
                                continue;
                            }

                            prevTransLayerSeq = curTransLayerSeq;
                            System.arraycopy(tmpBuf, 3 + cpyOffset, appLayerRxFifo.appLayerRxBuf, appLayerRxFifo.fifoBuf.writePos, tmpLen - 4 - cpyOffset);
                            FifoBuf tmp428_425 = appLayerRxFifo.fifoBuf;
                            tmp428_425.writePos += tmpLen - 4 - cpyOffset;
                            if ((tmpBuf[1] & 64) == 64) {
                                if (appLayerRxFifo.appLayerRxBuf[2] == 5 && (appLayerRxFifo.appLayerRxBuf[6] << 24) + (appLayerRxFifo.appLayerRxBuf[7] << 16) + (appLayerRxFifo.appLayerRxBuf[8] << 8) + appLayerRxFifo.appLayerRxBuf[9] < 1000000000) {
                                    Log.e("moofit-error", "TransLayerPullDat: ");
                                }

                                isPktEnd = true;
                            }
                        } else if ((tmpBuf[1] & 128) == 0) {
                            curRecvAppLayerPktSeq = (tmpBuf[3] << 8) + tmpBuf[4];
                            System.arraycopy(tmpBuf, 5, appLayerRxFifo.appLayerRxBuf, 0, tmpLen - 4 - 2);
                            appLayerRxFifo.fifoBuf.writePos = tmpLen - 4 - 2;
                            isPktEnd = true;
                        }

                        if (isPktEnd) {
                            Log.d("moofit-TransLayerDebug", "isPktEndcurseq: " + curRecvAppLayerPktSeq + "lastseq" + lastRecvAppLayerPktSeq);

                            try {
                                TransLayerSendAck(curRecvAppLayerPktSeq);
                            } catch (InterruptedException var7) {
                                var7.printStackTrace();
                            }

                            if (curRecvAppLayerPktSeq != lastRecvAppLayerPktSeq) {
                                lastRecvAppLayerPktSeq = curRecvAppLayerPktSeq;
                                appLayerRxFifo.fifoBuf.isAvailable = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    private static void TransLayerResetTxInfoPara() {
        Log.d("moofit-TransLayerDebug", "TransLayerResetTxInfoPara");
        transLayertxInfo.fifoBuf.isAvailable = false;
        transLayertxInfo.fifoBuf.writePos = 0;
        transLayertxInfo.fifoBuf.readPos = 0;
        transLayerTxCmprInfo.isNeedAck = false;
        transLayerTxCmprInfo.transLayerPktSeq = 0;
        transLayerTxCmprInfo.retryTimes = 0;
        transLayerTxCmprInfo.appLayerPktSeq = 0;
    }

    private static int EncodeTXData(byte[] buf) {
        int offset = 0;
        int surplusLen = transLayertxInfo.fifoBuf.writePos - transLayertxInfo.fifoBuf.readPos;
        offset = offset + 1;
        buf[offset] = -1;
        buf[offset++] = 0;
        buf[offset++] = 0;
        int dataPos = 3;
        int cpyDatLen = surplusLen > 16 ? 16 : surplusLen;
        if (surplusLen != 0) {
            if (transLayertxInfo.fifoBuf.readPos == 0) {
                transLayerTxCmprInfo.transLayerPktSeq = 0;
            }

            byte tmp131_130;
            if (surplusLen > cpyDatLen) {
                tmp131_130 = 1;
                buf[tmp131_130] = (byte)(buf[tmp131_130] | 128 | Util.CIRCULAR_INC_RET(0, transLayerTxCmprInfo.transLayerPktSeq, 16));
            } else if (transLayertxInfo.fifoBuf.readPos != 0) {
                tmp131_130 = 1;
                buf[tmp131_130] = (byte)(buf[tmp131_130] | 192 | Util.CIRCULAR_INC_RET(0, transLayerTxCmprInfo.transLayerPktSeq, 16));
            }

            System.arraycopy(transLayertxInfo.tranLayerTxBuf, transLayertxInfo.fifoBuf.readPos, buf, dataPos, cpyDatLen);
            offset += cpyDatLen;
            FifoBuf var10000 = transLayertxInfo.fifoBuf;
            var10000.readPos += cpyDatLen;
        }

        buf[2] = (byte)(offset + 1);
        buf[offset] = (byte) Util.get_checksum(0, buf, 0, offset);
        int len = offset + 1;
        return len;
    }

    public static int TransLayerRegroupAppLayerData(byte[] bytes, int len, TransLayerTxCpmrInfo txCmpr) {
        Log.d("moofit", "TransLayerRegroupAppLayerData len = " + len);
        if (transLayertxInfo.fifoBuf.isAvailable) {
            Log.d("moofit", "TransLayerRegroupAppLayerData busy");
            return -1;
        } else if (len > 100) {
            Log.d("moofit", "TransLayerRegroupAppLayerData exceed len");
            return -2;
        } else {
            transLayertxInfo.fifoBuf.isAvailable = true;
            transLayertxInfo.fifoBuf.readPos = 0;
            transLayertxInfo.fifoBuf.writePos = len;
            System.arraycopy(bytes, 0, transLayertxInfo.tranLayerTxBuf, 0, len);
            maxRetryTiems = txCmpr.isNrtData ? 20 : 20;
            transLayerTxCmprInfo.isNeedAck = txCmpr.isNeedAck;
            transLayerTxCmprInfo.appLayerPktSeq = txCmpr.appLayerPktSeq;
            transLayerTxCmprInfo.retryTimes = 0;
            transLayerTxCmprInfo.isNrtData = txCmpr.isNrtData;
            Log.d("moofit-TransLayerDebug", "regroup-app_seq: " + transLayerTxCmprInfo.appLayerPktSeq);
            BleAppLayerProcess.AppLayerTxEventHandle(0, transLayerTxCmprInfo.isNrtData);
            return 0;
        }
    }

    private static void TransLayerSendAck(int ackSeq) throws InterruptedException {
        boolean result = false;
        byte[] buf = new byte[6];
        int offset = 0;
        int sendCnt = 0;
        Log.d("moofit-TransLayerDebug", "TransLayerSendAck:isconnect: " + BleState.bleStatus);
        if (mBleService == null) {
            Log.d("moofit-TransLayerDebug", "TransLayerSendAck:mBleService is null: ");
        }

        int var5 = offset + 1;
        buf[offset] = -1;
        buf[var5++] = 32;
        buf[var5++] = 6;
        buf[var5++] = (byte)(ackSeq >> 8);
        buf[var5++] = (byte)ackSeq;
        buf[var5++] = (byte) Util.get_checksum(0, buf, 0, 5);

        while(!result && sendCnt < 5) {
            if (BleState.bleStatus == 15 || BleState.bleStatus == 19) {
                if (mBleService == null) {
                    return;
                }

                result = mBleService.write_notify(buf);
                Log.d("moofit-TransLayerDebug", "TransLayerSendAck: " + result);
                if (!result) {
                    ++sendCnt;
                    Thread.sleep(100L);
                }
            }
        }

    }

    private static void SendTxData(int free_pkts) throws InterruptedException {

        byte[] buf = new byte[20];
        int retryTimes = 0;

        do {
            while(transLayertxInfo.fifoBuf.readPos < transLayertxInfo.fifoBuf.writePos) {
                int sendLen = EncodeTXData(buf);
                byte[] sendBuf = new byte[sendLen];
                System.arraycopy(buf, 0, sendBuf, 0, sendLen);
                if (mBleService == null) {
                    return;
                }

                boolean result = mBleService.write_notify(sendBuf);
                System.out.print("send data:");

                for(int j = 0; j < sendLen; ++j) {
                    System.out.printf("%x ", sendBuf[j]);
                }

                if (!result) {
                    System.out.print("false\n");
                } else {
                    System.out.print("true\n");
                }

                if (!result) {
                    ++retryTimes;
                    if (retryTimes > 50) {
                        BleHelper.bleOpReConnect();
                        Log.e("moofit", "ble status: retryTimes > 50");
                    } else {
                        FifoBuf var10000 = transLayertxInfo.fifoBuf;
                        var10000.readPos -= sendBuf.length - 4;
                        Thread.sleep(100L);
                    }
                } else {
                    ++transLayerTxCmprInfo.transLayerPktSeq;
                    retryTimes = 0;
                }
            }
        } while(transLayertxInfo.fifoBuf.readPos < transLayertxInfo.fifoBuf.writePos);

        if (transLayerTxCmprInfo.isNeedAck) {
            BleAppLayerProcess.AppLayerTxEventHandle(1, transLayerTxCmprInfo.isNrtData);
        } else {
            TransLayerResetTxInfoPara();
            BleAppLayerProcess.AppLayerTxEventHandle(5, transLayerTxCmprInfo.isNrtData);
        }

        BleAppLayerProcess.AppLayerTxEventHandle(2, transLayerTxCmprInfo.isNrtData);
    }

    public static void TransLayerSendData(int free_pkts, boolean is_retry) {
        Log.d("moofit", "send is_retry" + is_retry);
        if (transLayertxInfo.fifoBuf.isAvailable) {
            if (is_retry) {
                if (transLayerTxCmprInfo.isNeedAck) {
                    ++transLayerTxCmprInfo.retryTimes;
                    Log.d("moofit-TransLayerDebug", "send again" + transLayerTxCmprInfo.retryTimes);
                    if (transLayerTxCmprInfo.retryTimes > 5) {
                        TransLayerResetTxInfoPara();
                        BleAppLayerProcess.AppLayerTxEventHandle(4, transLayerTxCmprInfo.isNrtData);
                    } else {
                        transLayertxInfo.fifoBuf.readPos = 0;

                        try {
                            SendTxData(free_pkts);
                        } catch (InterruptedException var4) {
                            var4.printStackTrace();
                        }
                    }
                }
            } else {
                try {
                    SendTxData(free_pkts);
                } catch (InterruptedException var3) {
                    var3.printStackTrace();
                }
            }
        }

    }

    public static void TransLayerResetPara() {
        mBleService = null;
        lastRecvAppLayerPktSeq = 0;
        TransLayerResetTxInfoPara();
        TransLayerResetRxInfoPara();
    }

    public static class FifoBuf {
        public boolean isAvailable = false;
        public int fifoUsageLen = 0;
        public int readPos = 0;
        public int writePos = 0;

        public FifoBuf() {
        }
    }

    public static class TransLayerRxInfo {
        FifoBuf fifoBuf = new FifoBuf();
        public static int[] tranLayerRxBuf = new int[140];

        public TransLayerRxInfo() {
        }
    }

    public static class TransLayerTxCpmrInfo {
        public boolean isNeedAck = false;
        public boolean isNrtData = false;
        public int retryTimes = 0;
        public int transLayerPktSeq = 0;
        public int appLayerPktSeq = 0;

        public TransLayerTxCpmrInfo() {
        }
    }

    static class TransLayerTxInfo {
        FifoBuf fifoBuf = new FifoBuf();
        private byte[] tranLayerTxBuf = new byte[100];

        TransLayerTxInfo() {
        }
    }
}
