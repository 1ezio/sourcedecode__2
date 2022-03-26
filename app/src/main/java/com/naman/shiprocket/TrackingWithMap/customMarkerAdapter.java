package com.naman.shiprocket.TrackingWithMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.naman.shiprocket.R;

public class customMarkerAdapter implements GoogleMap.InfoWindowAdapter{

    private final View view;
    private Context context;

    public customMarkerAdapter(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.mapmarker,null);
    }

    private void renderingText(Marker marker, View view){
        String title = marker.getTitle();
        TextView titleText = view.findViewById(R.id.title);

        if(!title.equals("")){
            titleText.setText(title);
        }
        String description = marker.getSnippet();
        TextView descriptionText = view.findViewById(R.id.description);
        if(!description.equals("")){
            descriptionText.setText(description);
        }
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderingText(marker, view);
        return view;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderingText(marker, view);
        return view;
    }
}
