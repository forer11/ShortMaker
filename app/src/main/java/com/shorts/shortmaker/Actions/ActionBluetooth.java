package com.shorts.shortmaker.Actions;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shorts.shortmaker.ActionDialogs.ActionDialog;
import com.shorts.shortmaker.ActionDialogs.SetBluetoothDialog;

import java.util.List;


public class ActionBluetooth implements Action {
    private static final String DEVICE_DOES_NOT_HAVE_BLUETOOTH_CAPABILITIES = "device doesn't have bluetooth capabilities";
    private SetBluetoothDialog dialog;
    private Boolean state;
    private BluetoothAdapter bluetoothAdapter;

    public ActionBluetooth() {
        this.dialog = new SetBluetoothDialog();
    }

    @Override
    public void activate(final Context context, final Context activity, boolean isNewTask) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Dexter.withContext(activity)
                .withPermission(Manifest.permission.BLUETOOTH)
                .withListener(new PermissionListener() {
                    @Override
                    public void
                    onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (state) {
                            enableBluetooth(activity);
                        } else {
                            disableBluetooth(activity);
                        }
                    }

                    @Override
                    public void
                    onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void
                    onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,
                                                       PermissionToken permissionToken) {

                    }
                }).check();
    }

    @Override
    public ActionDialog getDialog() {
        return dialog;
    }

    @Override
    public void setData(List<String> data) {
        state = data.get(0).equals(SetBluetoothDialog.ON);
    }

    public void enableBluetooth(Context activity) {
        if (bluetoothAdapter == null) {
            Log.d("ERROR", DEVICE_DOES_NOT_HAVE_BLUETOOTH_CAPABILITIES);
            Toast.makeText(activity,
                    DEVICE_DOES_NOT_HAVE_BLUETOOTH_CAPABILITIES,
                    Toast.LENGTH_SHORT).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public void disableBluetooth(Context activity) {
        if (bluetoothAdapter == null) {
            Log.d("ERROR", DEVICE_DOES_NOT_HAVE_BLUETOOTH_CAPABILITIES);
            Toast.makeText(activity,
                    DEVICE_DOES_NOT_HAVE_BLUETOOTH_CAPABILITIES,
                    Toast.LENGTH_SHORT).show();
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }
}
