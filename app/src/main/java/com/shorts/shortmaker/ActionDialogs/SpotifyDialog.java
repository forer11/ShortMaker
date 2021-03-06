package com.shorts.shortmaker.ActionDialogs;

import android.app.Dialog;
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

import com.shorts.shortmaker.R;

import java.util.ArrayList;


public class SpotifyDialog extends ActionDialog {

    private EditText artist;
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
            if (artist.getText().toString().equals("")) {
                artist.setError("Enter an artist");
                okButton.setEnabled(false);
            } else {
                artist.setError(null);
                okButton.setEnabled(true);
            }
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.spotify_dialog, null);

        initializeDialogViews(view);

        buildDialog(builder, view, "Which artist to search on Spotify", okButton);
        return builder.create();
    }

    protected void initializeDialogViews(View view) {
        artist = view.findViewById(R.id.editText);
        ImageView imageView = view.findViewById(R.id.imageView);
        setDialogImage(imageView, R.drawable.spotify_gif);
        okButton = view.findViewById(R.id.okButton);
        artist.addTextChangedListener(userInputTextWatcher);
    }


    protected void getUserInput() {
        String album = artist.getText().toString();
        ArrayList<String> results = new ArrayList<>();
        results.add(album);
        String description = "Spotify";
        listener.applyUserInfo(results, description);
    }
}
