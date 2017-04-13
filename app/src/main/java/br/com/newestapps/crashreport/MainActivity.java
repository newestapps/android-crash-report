package br.com.newestapps.crashreport;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.newestapps.crashreport.nwcr.NWCrashReport;
import br.com.newestapps.crashreport.nwcr.NWCrashReportConfig;
import br.com.newestapps.crashreport.nwcr.NWCrashReportSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        }, 2000);

    }
}
