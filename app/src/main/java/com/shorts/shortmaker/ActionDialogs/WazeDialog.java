package com.shorts.shortmaker.ActionDialogs;

import android.app.Dialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.shorts.shortmaker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class WazeDialog extends ActionDialog {

    private String apiId;
    private Button okButton;
    private String finalName;
    private String finalAddress;
    PlacesClient placesClient;
    private View view;
    private String addressDataFormat;
    private LatLng latLng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiId = getString(R.string.maps_id);
        if (!Places.isInitialized()) {
            Places.initialize(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    apiId);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        placesClient = Places.createClient(Objects.requireNonNull(getContext()));

        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) Objects.requireNonNull(getActivity())
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);
        Objects.requireNonNull(autocompleteSupportFragment)
                .setPlaceFields(Arrays.asList(Place.Field.ID,
                        Place.Field.LAT_LNG,
                        Place.Field.NAME,
                        Place.Field.ADDRESS,
                        Place.Field.ADDRESS_COMPONENTS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final String locationName = place.getName();
                latLng = place.getLatLng();
                final String locationAddress = place.getAddress();
                if (locationName != null && latLng != null) {
                    okButton.setEnabled(true);
                    finalName = locationName;
                    finalAddress = locationAddress;
                    addressDataFormat = finalAddress.replace(",", "");
                    addressDataFormat = addressDataFormat.replace(" ", "%20");
                } else {
                    okButton.setEnabled(false);
                    Toast.makeText(getContext(),
                            "Place is invalid, try choosing something else",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
        final ImageButton imageButton = Objects.requireNonNull(autocompleteSupportFragment.getView())
                .findViewById(R.id.places_autocomplete_clear_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = autocompleteSupportFragment
                        .getView().findViewById(R.id.places_autocomplete_search_input);
                editText.setText("");
                okButton.setEnabled(false);
                finalName = "";
                finalAddress = "";
                imageButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.waze_dialog, null);

        initializeDialogViews(builder, view);
        return builder.create();
    }

    protected void initializeDialogViews(AlertDialog.Builder builder, View view) {
        ImageView imageView = view.findViewById(R.id.imageView);
        setDialogImage(imageView, R.drawable.waze_gif);
        okButton = view.findViewById(R.id.okButton);
        buildDialog(builder, view, "Where to set Waze to", okButton);
    }


    protected void getUserInput() {
        ArrayList<String> results = new ArrayList<>();
        results.add(addressDataFormat);
        results.add(Double.toString(latLng.latitude));
        results.add(Double.toString(latLng.longitude));
        results.add(finalName);
        String description = "Navigate to " + finalAddress; //TODO decide
        listener.applyUserInfo(results, description);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Fragment fragment = (Objects.requireNonNull(getFragmentManager())
                .findFragmentById(R.id.autocomplete_fragment));
        FragmentTransaction ft = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
        ft.remove(Objects.requireNonNull(fragment));
        ft.commit();
    }
}
