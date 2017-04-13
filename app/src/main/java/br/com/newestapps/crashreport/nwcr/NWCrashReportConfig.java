package br.com.newestapps.crashreport.nwcr;

public class NWCrashReportConfig {

    public static final int SEND_PARALLEL = 1;
    public static final int SEND_SERIAL = 2;

    public static NWCrashReportConfig create() {
        NWCrashReportConfig i = new NWCrashReportConfig();
        return i;
    }

    ////////////////////////////////////////////////////////////////////////

    private String serverBaseUrl;
    private Class<? extends NWCrashReportSender> sender;
    private int sendMode = SEND_PARALLEL;
    private String deviceUuid;

    ////////////////////////////////////////////////////////////////////////

    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

    public NWCrashReportConfig serverBaseUrl(String serverBaseUrl) {
        this.serverBaseUrl = serverBaseUrl;
        return this;
    }

    public Class<? extends NWCrashReportSender> getSenderClass() {
        return sender;
    }

    public NWCrashReportConfig senderClass(Class<? extends NWCrashReportSender> sender) {
        this.sender = sender;
        return this;
    }

    public int getSendMode() {
        return sendMode;
    }

    public NWCrashReportConfig sendMode(int sendMode) {
        this.sendMode = sendMode;
        return this;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public NWCrashReportConfig deviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
        return this;
    }
}
