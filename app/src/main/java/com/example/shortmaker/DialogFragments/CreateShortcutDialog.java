package com.example.shortmaker.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.example.shortmaker.ActionDialogs.ActionDialog;
import com.example.shortmaker.Activities.MainActivity;
import com.example.shortmaker.Activities.SetActionsActivity;
import com.example.shortmaker.AppData;
import com.example.shortmaker.DataClasses.Shortcut;
import com.example.shortmaker.FireBaseHandlers.FireStoreHandler;
import com.example.shortmaker.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CreateShortcutDialog extends AppCompatDialogFragment {

    private Context context;
    private int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = Objects.requireNonNull(getArguments()).getInt("pos");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.create_shortcut_dialog, null);
        final AppData appData = (AppData) getApplicationContext();
        final EditText shortcutTitleEditText = view.findViewById(R.id.shortcutTitle);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.progress_gif).into(imageView);

        builder.setView(view)
                .setTitle("Add a shortcut")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String title = shortcutTitleEditText.getText().toString();
                        if (title.equals("")) {
                            Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_SHORT).show();
                        } else {
                            Shortcut shortcut = new Shortcut(title,
                                    FireStoreHandler.DEFAULT_IMAGE_URL);
                            shortcut.setPos(pos);
                            appData.fireStoreHandler.addShortcut(shortcut,
                                    new FireStoreHandler.SingleShortcutCallback() {
                                        @Override
                                        public void onAddedShortcut(String id,
                                                                    Shortcut shortcut1,
                                                                    Boolean success) {
                                            if (success) { //TODO if not successful
                                                Intent intent = new Intent(context,
                                                        SetActionsActivity.class);
                                                intent.putExtra("shortcutId", id);
                                                context.startActivity(intent);
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                        }
                    }
                });
        return builder.create();
    }


}