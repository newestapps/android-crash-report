package br.com.newestapps.crashreport;

import android.app.Application;

import br.com.newestapps.crashreport.nwcr.NWCrashReport;
import br.com.newestapps.crashreport.nwcr.NWCrashReportConfig;
import br.com.newestapps.crashreport.nwcr.NWCrashReportSender;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NWCrashReport.init(this, NWCrashReportConfig.create()
                .serverBaseUrl("http://192.168.11.124:80/newestapps/crashreport/register")
                .sendMode(NWCrashReportConfig.SEND_PARALLEL)
                .senderClass(NWCrashReportSender.DEFAULT_SENDER));
    }

}
