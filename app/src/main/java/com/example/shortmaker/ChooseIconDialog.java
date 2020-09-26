package com.example.shortmaker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortmaker.ActionDialogs.ActionDialog;
import com.example.shortmaker.Actions.Action;
import com.example.shortmaker.Actions.ActionAlarmClock;
import com.example.shortmaker.Actions.ActionPhoneCall;
import com.example.shortmaker.Actions.ActionSendTextMessage;
import com.example.shortmaker.Actions.ActionSoundSettings;
import com.example.shortmaker.Actions.ActionSpotify;
import com.example.shortmaker.Actions.ActionWaze;
import com.example.shortmaker.Adapters.ChooseActionAdapter;
import com.example.shortmaker.Adapters.ChooseIconAdapter;
import com.example.shortmaker.DataClasses.Icon;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChooseIconDialog extends AppCompatDialogFragment {

    private Context context;
    private OnIconPick callback;

    public interface OnIconPick {
        public void onIconPick(String iconLink);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            callback = (OnIconPick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnIconPick");
        }
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.choose_action_dialog, null);
        setRecyclerView(view);
        builder.setTitle("Choose an Icon");
        builder.setView(view);
        return builder.create();
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ChooseIconAdapter adapter = new ChooseIconAdapter(context, (AppData) context.getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(adapter);
        iconClickHandler(adapter);
    }

    private void iconClickHandler(ChooseIconAdapter adapter) {
        adapter.setOnIconClickListener(new ChooseIconAdapter.OnIconClickListener() {
            @Override
            public void onIconClick(int position) {
                Icon icon = ((AppData) context.getApplicationContext()).getIcons().get(position);
                callback.onIconPick(icon.getLink());
                dismiss();
            }
        });
    }
}