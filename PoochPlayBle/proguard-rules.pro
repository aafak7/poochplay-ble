# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.amit.poochplayble.BleService{*;}
-keep class com.amit.poochplayble.BleCallBacks{*;}
-keep class com.amit.poochplayble.BleCallRemindCmd{*;}
-keep class com.amit.poochplayble.Array{*;}
-keep class com.amit.poochplayble.ProtocolHanderManager{*;}
-keep class com.amit.poochplayble.AlarmPlanData{*;}
-keep class com.amit.poochplayble.BleAppLayerDataProcess{*;}
-keep class com.amit.poochplayble.BleAppLayerProcess{*;}
-keep class com.amit.poochplayble.BleConstant{*;}
-keep class com.amit.poochplayble.BleData{*;}
-keep class com.amit.poochplayble.WaterSetInfo{*;}
-keep class com.amit.poochplayble.Utils{*;}
-keep class com.amit.poochplayble.UploadSleep{*;}
-keep class com.amit.poochplayble.TimeUtils{*;}
-keep class com.amit.poochplayble.SysTimerManager{*;}
-keep class com.amit.poochplayble.SysSendMsg{*;}
-keep class com.amit.poochplayble.SysHanderManager{*;}
-keep class com.amit.poochplayble.SysApplication{*;}
-keep class com.amit.poochplayble.SuccessCanSetInfo{*;}
-keep class com.amit.poochplayble.StepData{*;}
-keep class com.amit.poochplayble.SleepData{*;}
-keep class com.amit.poochplayble.SharedPreUtils{*;}
-keep class com.amit.poochplayble.SampleGattAttributes{*;}
-keep class com.amit.poochplayble.RemindStutasInfo{*;}
-keep class com.amit.poochplayble.ProtocolTimerManager{*;}
-keep class com.amit.poochplayble.Nrtanalysis{*;}
-keep class com.amit.poochplayble.MyCallInterface{*;}
-keep class com.amit.poochplayble.MoveSetInfo{*;}
-keep class com.amit.poochplayble.JumpData{*;}
-keep class com.amit.poochplayble.HexUtil{*;}
-keep class com.amit.poochplayble.HeartRateDataInfo{*;}
-keep class com.amit.poochplayble.HeartBleService{*;}
-keep class com.amit.poochplayble.GattServices{*;}
-keep class com.amit.poochplayble.Funtion{*;}
-keep public class com.amit.poochplayble.*

-repackageclasses ''
-overloadaggressively
