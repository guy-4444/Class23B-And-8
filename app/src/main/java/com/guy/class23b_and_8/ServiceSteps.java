package com.guy.class23b_and_8;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ServiceSteps extends Service {

    public static final String FM_99 = "FM_99";
    public static final String DOWNLOAD_PROGRESS = "DOWNLOAD_PROGRESS";

    public static final String ServiceSteps_ACTION_START = "ServiceSteps_ACTION_START";
    public static final String ServiceSteps_ACTION_STOP = "ServiceSteps_ACTION_STOP";

    private boolean isDownload = false;


    private MediaPlayer player;


    private boolean isMusicPlaying = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("pttt", "MyService onStartCommand");

        String action = intent.getAction();
        if (action.equals(ServiceSteps_ACTION_START)) {
            if (!isMusicPlaying) {
                new Thread(() -> doSomething()).start();
                startMusic();
            }
        } else if (action.equals(ServiceSteps_ACTION_STOP)) {
            if (isMusicPlaying) {
                isDownload = false;
                stopMusic();
            }
        }
        return START_STICKY;
    }

    private void startMusic() {
        isMusicPlaying = true;
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();
    }

    private void stopMusic() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }

        stopSelf();
    }

    private void doSomething() {
        isDownload = true;
        Log.d("pttt", "doSomething " + Thread.currentThread().getName());
        int x = 9;
        for (int i = 0; i < 10; i++) {
            if (isDownload) {
                Log.d("pttt", "i= " + i);
                int y = 0;
                for (int j = 0; j < 30000000; j++) {

                    if (j % 2 == 0) {
                        y += Math.pow(i, 3) + Math.sin(x * 2.4);
                    } else {
                        y -= Math.pow(i, 3) - Math.sin(x * 2.4);
                    }
                }
            }

            Intent intent = new Intent(FM_99);
            intent.putExtra(DOWNLOAD_PROGRESS, i);
            sendBroadcast(intent);
            //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

        stopMusic();
        isMusicPlaying = false;
        stopSelf();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}