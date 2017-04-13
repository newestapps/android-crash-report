package br.com.newestapps.crashreport.nwcr;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.newestapps.crashreport.BuildConfig;

public abstract class NWCrashReportSender {

    protected static final String TAG = NWCrashReport.TAG;
    public static final Class<? extends NWCrashReportSender> DEFAULT_SENDER = NWCrashReportSender_NWAdmin.class;

    private NWCrashReport crashReport;
    private Thread thread;
    private Throwable throwable;

    public NWCrashReportConfig getConfig() {
        return crashReport.getConfig();
    }

    public NWCrashReportSender(NWCrashReport crashReport, Thread thread, Throwable throwable) {
        this.crashReport = crashReport;
        this.thread = thread;
        this.throwable = throwable;
    }

    /**
     * Show the exception in logcat
     *
     * @return
     */
    public void log() {
        if (throwable != null) {
            Log.d(TAG, "######################################################");
            Log.d(TAG, "################# EXCEPTION DETECTED #################");
            Log.d(TAG, "######################################################");
            Log.d(TAG, " ");
            throwable.printStackTrace();
            Log.d(TAG, " ");
        }
    }

    public JSONObject getPreparedData() throws JSONException {
        JSONObject ob = new JSONObject();
        JSONObject app = new JSONObject();
        JSONObject device = new JSONObject();

        app.put("version", BuildConfig.VERSION_CODE);
        app.put("package", crashReport.getAppPackageName());
        ob.put("app", app);

        device.put("uuid", crashReport.getDeviceUuid());
        ob.put("device", device);

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        ob.put("createdAt", dt.format(new Date()));

        StackTraceElement[] traceElements = throwable.getStackTrace();
        Log.d(TAG, "traceElements.length:" + traceElements.length);

        JSONArray stackElements = new JSONArray();
        for (StackTraceElement el : traceElements) {
            JSONObject eee = new JSONObject();
            eee.put("classname", el.getClassName());
            eee.put("filename", el.getFileName());
            eee.put("linenumber", el.getLineNumber());
            eee.put("methodname", el.getMethodName());
            stackElements.put(eee);
        }

        String base64encodedStackTrace = throwable.toString();
        ob.put("stacktrace", base64encodedStackTrace);
        ob.put("elements", stackElements);
        ob.put("cause", throwable.getCause());
        ob.put("message", throwable.getMessage());

        JSONObject tdata = new JSONObject();
        tdata.put("name", thread.getName());
        tdata.put("priority", thread.getPriority());
        tdata.put("state", thread.getState());
        tdata.put("group", thread.getThreadGroup().getName());

        ob.put("thread", tdata);

        return ob;
    }

    public abstract void send();

}
