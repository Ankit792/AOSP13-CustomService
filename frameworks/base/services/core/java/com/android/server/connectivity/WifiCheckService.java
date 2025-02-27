package com.android.server.connectivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import android.util.Log;

public class WifiCheckService extends Service {
    private Handler handler;
    private Runnable wifiCheckRunnable;
    private static final long CHECK_INTERVAL = 30 * 1000; // 30 seconds
    private static final String TAG = "AnkitWifi";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        handler = new Handler(Looper.getMainLooper());
        wifiCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkWifiStatus();
                Log.w(TAG, "WifiCheckService on create for network");

                handler.postDelayed(this, CHECK_INTERVAL);
            }
        };
        handler.post(wifiCheckRunnable);
    }

    private void checkWifiStatus() {
        Log.d(TAG, "Checking WiFi status");
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi == null || !wifi.isConnected()) {
            Log.d(TAG, "WiFi is disconnected");
            Log.w(TAG, "isconnected on create for network");
            showWifiDisconnectedMessage();
        } else {
            Log.d(TAG, "WiFi is connected");
        }
    }

    private void showWifiDisconnectedMessage() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            Toast.makeText(WifiCheckService.this, "WiFi disconnected", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Toast displayed: WiFi disconnected");
        });
        Log.d(TAG, "Toast displayed: WiFi disconnected");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(wifiCheckRunnable);
    }
}
