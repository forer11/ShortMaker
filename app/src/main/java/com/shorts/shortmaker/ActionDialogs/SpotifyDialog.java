package com.shorts.shortmaker.ActionDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.shorts.shortmaker.R;

import java.util.ArrayList;


public class SpotifyDialog extends ActionDialog {

    private EditText albumToPlay;
    private Button okButton;
    private TextWatcher userInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (albumToPlay.getText().toString().equals("")) {
                albumToPlay.setError("Enter an album");
                okButton.setEnabled(false);
            } else {
                albumToPlay.setError(null);
                okButton.setEnabled(true);
            }
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.spotify_dialog,null);

        initializeDialogViews(view);

        buildDialog(builder, view);
        return builder.create();
    }

    protected void initializeDialogViews(View view) {
        albumToPlay = view.findViewById(R.id.editText);
        ImageView imageView = view.findViewById(R.id.imageView);
        setDialogImage(imageView, R.drawable.spotify_gif);
        okButton = view.findViewById(R.id.okButton);
        albumToPlay.addTextChangedListener(userInputTextWatcher);
    }

    protected void buildDialog(AlertDialog.Builder builder, View view) {
        builder.setView(view)
                .setTitle("Which album to play on Spotify");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                dismiss();
            }
        });
        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected void getUserInput() {
        String album = albumToPlay.getText().toString();
        ArrayList<String> results = new ArrayList<>();
        results.add(album);
        listener.applyUserInfo(results);
    }
}
