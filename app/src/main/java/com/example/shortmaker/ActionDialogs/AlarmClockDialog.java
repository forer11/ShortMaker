package com.example.shortmaker.ActionDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.shortmaker.R;

import java.util.ArrayList;

public class AlarmClockDialog extends ActionDialog{


    private EditText editTextAddress;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alarm_clock_dialog,null);

        editTextAddress = view.findViewById(R.id.editText);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.alarm_clock_gif).into(imageView);

        builder.setView(view)
                .setTitle("Open Spotify")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String address = editTextAddress.getText().toString();
                        ArrayList<String> results = new ArrayList<>();
                        results.add(address);
                        listener.applyUserInfo(results);
                    }
                });


        return builder.create();
    }



    public interface AlarmClockDialogListener {
        void applyText(String address);
    }
}
