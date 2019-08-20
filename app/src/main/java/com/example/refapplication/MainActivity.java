package com.example.refapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.installreferrer.api.*;

import android.os.RemoteException;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements InstallReferrerStateListener {

    private InstallReferrerClient referrerClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referrerClient = InstallReferrerClient.newBuilder(this).build();
        referrerClient.startConnection(this);
    }

    @Override
    public void onInstallReferrerSetupFinished(int responseCode) {

        switch (responseCode) {
            case InstallReferrerClient.InstallReferrerResponse.OK:
                // 接続完了
                try {
                    ReferrerDetails response = referrerClient.getInstallReferrer();
                    String installReferrer = response.getInstallReferrer();
                    TextView textView = findViewById(R.id.text);
                    textView.setText(installReferrer);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                // APIがサポートされない
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                // Play Storeアプリのアップデート中などで接続できなかった
                break;
        }
    }

    @Override
    public void onInstallReferrerServiceDisconnected() {
        // Try to restart the connection on the next request to
        // Google Play by calling the startConnection() method.
    }
}
