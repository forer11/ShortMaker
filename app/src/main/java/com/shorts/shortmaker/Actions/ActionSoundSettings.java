package com.shorts.shortmaker.Actions;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.shorts.shortmaker.ActionDialogs.ActionDialog;
import com.shorts.shortmaker.ActionDialogs.SoundSettingsDialog;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.shorts.shortmaker.AppData.inBackground;

public class ActionSoundSettings implements Action {

    private static final int SILENT_MODE = 1;
    private static final int VIBRATE_MODE = 2;
    private static final int RING_MODE = 3;
    private int mode;
    private SoundSettingsDialog dialog;

    public ActionSoundSettings() {
        this.dialog = new SoundSettingsDialog();
    }


    @Override
    public void activate(Application application, Context context) {
        Log.v("YAY", "sound setting activated");
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            switch (mode) {
                case SILENT_MODE:
                    putPhoneOnSilent(audioManager, context, inBackground);
                    break;
                case VIBRATE_MODE:
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    break;
                case RING_MODE:
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
            }
        }
    }

    @Override
    public ActionDialog getDialog() {
        return dialog;
    }

    @Override
    public void setData(List<String> data) {
        mode = Integer.parseInt(data.get(0));
    }

    private void putPhoneOnSilent(AudioManager audioManager, Context context, boolean isNewTask) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            if (isNewTask) {
                intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
            }
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
}
