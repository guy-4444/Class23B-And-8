package com.guy.class23b_and_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class Activity_Game extends AppCompatActivity {

    private MaterialButton game_BTN_stop;
    private LinearProgressIndicator game_PRG_download;


    BroadcastReceiver radio_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("pttt", Thread.currentThread().getName());
            if (intent == null) {
                return;
            }

            runOnUiThread(() -> {
                int prg = intent.getIntExtra(ServiceSteps.DOWNLOAD_PROGRESS, 0);
                updateProgress(prg);
            });

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        LocalBroadcastManager.getInstance(this).registerReceiver(radio_receiver, new IntentFilter(ServiceSteps.FM_99));
        registerReceiver(radio_receiver, new IntentFilter(ServiceSteps.FM_99));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(radio_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game_BTN_stop = findViewById(R.id.game_BTN_stop);
        game_PRG_download = findViewById(R.id.game_PRG_download);
        game_BTN_stop.setOnClickListener(v -> stop());

        game_PRG_download.setMax(10);
        game_PRG_download.setProgress(0);
    }

    private void updateProgress(int prg) {
        game_PRG_download.setProgress(prg + 1);
    }

    private void stop() {
        Intent intent = new Intent(this, ServiceSteps.class);
        intent.setAction(ServiceSteps.ServiceSteps_ACTION_STOP);
        startService(intent);
    }
}