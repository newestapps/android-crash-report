package br.com.newestapps.crashreport.nwcr;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class NWCrashReport {

    public static final String TAG = "NWCrashReport";

    private static final String SHARED_PREFERENCES_BUCKET = "NWCR_B";
    private static final String KEY_DEVICE_UUID = "device_uuid";

    private static NWCrashReport instance;
    private NWCrashReportConfig config;
    private String deviceUuid = null;
    private String appPackageName;

    public static void init(Context context, NWCrashReportConfig config) {
        if (instance == null) {
            instance = new NWCrashReport(context, config);
        }
    }

    public static NWCrashReport getInstance() {
        return instance;
    }

    public NWCrashReportConfig getConfig() {
        return config;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public NWCrashReport(Context context, NWCrashReportConfig config) {
        this.config = config;
        this.appPackageName = context.getPackageName();

        SharedPreferences nwcrPrefs = context.getSharedPreferences(SHARED_PREFERENCES_BUCKET, Context.MODE_PRIVATE);
        this.deviceUuid = nwcrPrefs.getString(KEY_DEVICE_UUID, null);

        if (this.deviceUuid == null) {
            SharedPreferences.Editor editor = nwcrPrefs.edit();
            String generatedUuid = UUID.randomUUID().toString();
            editor.putString(KEY_DEVICE_UUID, generatedUuid);
            editor.commit();
        }

        final NWCrashReport crashReport = this;

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Class<? extends NWCrashReportSender> senderClass = getConfig().getSenderClass();

                try {
                    Constructor senderConstructor = senderClass.getConstructor(
                            NWCrashReport.class,
                            Thread.class,
                            Throwable.class);

                    final NWCrashReportSender sender = (NWCrashReportSender) senderConstructor.newInstance(crashReport, t, e);
                    sender.log();

//                    AsyncTask task = new AsyncTask<Void, Void, Void>() {
//                        @Override
//                        protected Void doInBackground(Void... voids) {
//                            Log.d(TAG, "Sending report to: " + sender.getConfig().getServerBaseUrl());
                    sender.send();
//                            return null;
//                        }
//                    };
//
//                    if (getConfig().getSendMode() == NWCrashReportConfig.SEND_PARALLEL) {
//                        Log.d(TAG, "Parallel");
//                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sender);
//                    } else {
//                        Log.d(TAG, "Serial");
//                        task.execute(sender);
//                    }

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

        });

//        Looper.loop();
    }

//    @Override
//    public void uncaughtException(Thread t, Throwable e) {
//
//    }

}
