package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class BleMarco {
    public BleMarco() {
    }

    public static class BroadcastReceiverCode {
        public static final int BRACELET_CONNECTEED = 3;
        public static final int BRACELET_DISCONNECTEED = 4;
        public static final int BRACELET_DISCOVERED = 1;
        public static final int BRACELET_AVAILABLE = 2;
        public static final int BRACELET_SUCCESSSETINFO = 5;
        public static final int BRACELET_NRTDATAEND = 6;
        public static final int HEART_CONNECTEED = 7;
        public static final int HEART_DISCOVERED = 8;

        public BroadcastReceiverCode() {
        }
    }

    public class HeadrTail {
        public static final int PROTOCOL_VERSION = 16;
        public static final int UTILIZE_PRIVATE = 24;

        public HeadrTail() {
        }
    }

    public static class ProtocolErrCode {
        public static final int PROTOCOL_SUCCESS = 0;
        public static final int PROTOCOL_DEFAULT = 65262;
        public static final int PROTOCOL_TRANSLAYER_SUCCESS = 0;
        public static final int PROTOCOL_TRANSLAYER_ENCODE_BUSY = -1;
        public static final int PROTOCOL_TRANSLAYER_ENCODE_EXCEED_LEN = -2;
        public static final int PROTOCOL_APPLAYER_SUCCESS = 0;
        public static final int PROTOCOL_APPLAYER_ENCODE_FAIL = -1;
        public static final int PROTOCOL_APPLAYER_ENCODE_EXCEED_LEN = -2;
        public static final int PROTOCOL_APPLAYER_ENCODE_CHANNEL_CLOSED = -3;

        public ProtocolErrCode() {
        }
    }

    public class SysEvent {
        public static final int BLE_OP_STATUS_CONNECT_EVT = 1;
        public static final int BLE_OP_STATUS_DISCONNECT_EVT = 3;
        public static final int BLE_OP_STATUS_CLOSE_EVT = 4;
        public static final int BLE_STATUS_CONNECTING = 12;
        public static final int BLE_STATUS_CONNECTED = 13;
        public static final int BLE_STATUS_DISCOVERING = 14;
        public static final int BLE_STATUS_DISCOVERED = 15;
        public static final int BLE_STATUS_AVAILABLE = 19;
        public static final int BLE_STATUS_DISCONNECTING = 16;
        public static final int BLE_STATUS_DISCONNECTED = 17;
        public static final int BLE_STATUS_CLOSEED = 18;
        public static final int BLE_OPEN_NOT_REALTIME_CHANNEL_EVT = 30;
        public static final int BLE_OPEN_REALTIME_CHANNEL_EVT = 31;
        public static final int BLE_CLOSE_NOT_REALTIME_CHANNEL_EVT = 32;
        public static final int OLD_PROTOCOL_DECODE_DATA = 34;
        public static final int BLE_OLD_PROTOCOL_NOTIFY_ON = 51;
        public static final int BLE_OLD_PROTOCOL_NOTIFY_OFF = 52;
        public static final int BLE_OLD_PROTOCOL_SYNC_NRT_IDLE = 60;
        public static final int BLE_OLD_PROTOCOL_SYNC_NRT_BUSY = 61;
        public static final int BLE_WAIT_FOR_DISCOVERED_NEW_EVT = 66;
        public static final int BLE_OPEN_HEART_CHANNEL_EVT = 80;

        public SysEvent() {
        }
    }

    public class cmd {
        public static final int ACK_CMDID = 253;
        public static final int UPLOAD_SET_CMDID = 1;
        public static final int UPLOAD_ANDRIOD_MSG_CMDID = 2;
        public static final int UPLOAD_GET_RT_DAT_CMDID = 3;
        public static final int UPLOAD_GET_NRT_DAT_CMDID = 4;
        public static final int DOWNLOAD_RT_DATA_CMDID = 1;
        public static final int DOWNLOAD_CTRL_CMDID = 2;
        public static final int DOWNLOAD_GET_RT_DAT_CMDID = 3;
        public static final int DOWNLOAD_GET_NRT_DAT_CMDID = 4;
        public static final int DOWNLOAD_VSESION_CMDID = 5;

        public cmd() {
        }
    }

    public class key {
        public static final int ACK_RECOGNITION_KEY = 1;
        public static final int ACK_NONRECOGNITION_KEY = 2;
        public static final int UPLOAD_SET_MESSAGE_REMIND_KEY = 1;
        public static final int UPLOAD_SET_ALARM_REMIND_KEY = 2;
        public static final int UPLOAD_SET_HEALTH_REMIND_KEY = 3;
        public static final int UPLOAD_SET_RT_DATA_REMIND_KEY = 4;
        public static final int UPLOAD_SET_ALARM_TIME_REMIND_KEY = 5;
        public static final int UPLOAD_SET_MOVE_TIME_KEY = 6;
        public static final int UPLOAD_SET_WATER_TIME_KEY = 7;
        public static final int UPLOAD_SET_UTC_KEY = 8;
        public static final int UPLOAD_SET_USERBODYINFO_KEY = 9;
        public static final int UPLOAD_SET_SLEEPINFO_KEY = 10;
        public static final int UPLOAD_ANDROID_CALL_KEY = 1;
        public static final int UPLOAD_ANDROID_MESSAGE_KEY = 2;
        public static final int UPLOAD_GET_DEVICE_SUPPORT_KEY = 1;
        public static final int UPLOAD_GET_LAST_SYNC_UTC_KEY = 2;
        public static final int UPLOAD_GET_NRT_ALL_DATA_KEY = 0;
        public static final int UPLOAD_GET_NRT_SPORT_DATA_KEY = 1;
        public static final int UPLOAD_GET_NRT_HEART_DATA_KEY = 2;
        public static final int UPLOAD_GET_NRT_SPED_CAD_DATA_KEY = 3;
        public static final int UPLOAD_GET_NRT_TRAIN_DATA_KEY = 4;
        public static final int UPLOAD_GET_NRT_SLEEP_DATA_KEY = 5;
        public static final int UPLOAD_GET_NRT_ONEDAY_DATA_KEY = 6;
        public static final int DOWNLOAD_RT_DATA_KEY = 1;
        public static final int DOWNLOAD_CTRL_FINDPHONE_KEY = 1;
        public static final int DOWNLOAD_DEVICE_SURPORT_KEY = 1;
        public static final int DOWNLOAD_LAST_SYNC_UTC_KEY = 2;
        public static final int DOWNLOAD_NRT_SPORT_DATA_KEY = 1;
        public static final int DOWNLOAD_NRT_HEART_DATA_KEY = 2;
        public static final int DOWNLOAD_NRT_SPD_CAD_DATA_KEY = 3;
        public static final int DOWNLOAD_NRT_TRAIN_DATA_KEY = 4;
        public static final int DOWNLOAD_NRT_SLEEP_DATA_KEY = 5;
        public static final int DOWNLOAD_NRT_ONEDAY_DATA_KEY = 6;
        public static final int DOWNLOAD_NRT_END_KEY = 255;
        public static final int DOWNLOAD_TRANS_LAYER_VERSION_KEY = 1;

        public key() {
        }
    }

    public class msgEvnet {
        public static final int BLEPM_SERVICE_1_EVT2_RECV_DAT_FROM_APP = 5;
        public static final int BLEPM_RT_GET_DATA_FROM_TRANS_LAYER = 6;
        public static final int BLEPM_SERVICE_1_EVT2_COMM_PROCESS = 7;
        public static final int BLEPM_RT_SEND_TRANS_LAYER_DAT_PKT_AGAIN_EVT = 8;
        public static final int BLEPM_RT_SEND_TRANS_LAYER_DAT_PKT_EVT = 9;
        public static final int BLEPM_RT_PUSH_DAT_TO_TRANS_LAYER = 10;
        public static final int COMM_ENCODE_SET_CMDID = 11;
        public static final int COMM_ENCODE_GET_CMDID = 12;
        public static final int COMM_ENCODE_ANDROIDINFO_CMDID = 13;
        public static final int COMM_ENCODE_GET_NRT_DATA_CMDID = 14;
        public static final int COMM_ENCODE_MSG_REMIND_KEY = 15;
        public static final int COMM_ENCODE_ALARM_REMIND_KEY = 16;
        public static final int COMM_ENCODE_HEALTH_REMIND_KEY = 17;
        public static final int COMM_ENCODE_RTDATA_REMIND_KEY = 18;
        public static final int COMM_ENCODE_ALARM_TIME_KEY = 19;
        public static final int COMM_ENCODE_MOVE_TIME_KEY = 20;
        public static final int COMM_ENCODE_WATER_TIME_KEY = 21;
        public static final int COMM_ENCODE_SET_UTC_KEY = 22;
        public static final int COMM_ENCODE_SET_USERBODYINFO_KEY = 23;
        public static final int COMM_ENCODE_SET_USERSLEEPINFO_KEY = 24;
        public static final int COMM_ENCODE_GET_DEVSUPPORT_KEY = 25;
        public static final int COMM_ENCODE_GET_SYNC_UTC_KEY = 26;
        public static final int COMM_ENCODE_ANDROID_CALL_KEY = 27;
        public static final int COMM_ENCODE_ANDROID_MSG_KEY = 28;
        public static final int COMM_ENCODE_GET_ALL_NRT_DATA_KEY = 29;
        public static final int COMM_ENCODE_GET_SPORT_NRT_DATA_KEY = 30;
        public static final int COMM_ENCODE_GET_HEART_NRT_DATA_KEY = 31;
        public static final int COMM_ENCODE_GET_SPD_CAD_NRT_DATA_KEY = 32;
        public static final int COMM_ENCODE_GET_TRAIN_NRT_DATA_KEY = 33;
        public static final int COMM_ENCODE_GET_SLEEP_NRT_DATA_KEY = 34;
        public static final int COMM_ENCODE_GET_ONEDAY_NRT_DATA_KEY = 35;
        public static final int PROTOCOL_RESET_EVT = 36;

        public msgEvnet() {
        }
    }
}
