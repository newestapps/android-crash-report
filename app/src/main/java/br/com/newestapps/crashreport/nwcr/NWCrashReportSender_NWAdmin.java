package br.com.newestapps.crashreport.nwcr;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NWCrashReportSender_NWAdmin extends NWCrashReportSender {

    public NWCrashReportSender_NWAdmin(NWCrashReport crashReport, Thread thread, Throwable throwable) {
        super(crashReport, thread, throwable);
    }

    @Override
    public void send() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    JSONObject data = getPreparedData();

                    URL url = new URL(getConfig().getServerBaseUrl());

                    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                    httpCon.setDoOutput(true);
                    httpCon.setRequestMethod("PUT");
                    OutputStreamWriter out = new OutputStreamWriter(
                            httpCon.getOutputStream());
                    out.write(data.toString());
                    out.close();
                    httpCon.getInputStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }
}
