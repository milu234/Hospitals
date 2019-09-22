package com.example.hospitals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.activity_info_page, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_map);
        TextView nameOfPlace = (TextView) v.findViewById(R.id.nameOfPlace);
        TextView vicinity = (TextView) v.findViewById(R.id.vicinity);





        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        String nameofplace = marker.getTitle();
        if (nameofplace.contains("Hospital") || (nameofplace.contains("Maternity")) || nameofplace.contains("Nursing Home") || nameofplace.contains("Dawakhana")){
            imageView.setImageResource(R.mipmap.info_hospital);
        }
        if(nameofplace.contains("Medical") || nameofplace.contains("Pharma") || nameofplace.contains("Pharmacy") || nameofplace.contains("pharma")|| nameofplace.contains("Aushadpedhi")|| nameofplace.contains("Pet")|| nameofplace.contains("Wellness") || nameofplace.contains("Apollo") || nameofplace.contains("Life") ) {
            imageView.setImageResource(R.mipmap.suplements);
        }

        if (nameofplace.contains("Blood") || (nameofplace.contains("Bank")) || (nameofplace.contains("Clinic")) ) {
            imageView.setImageResource(R.mipmap.blood_bank);
        }
        nameOfPlace.setText(marker.getTitle());
        vicinity.setText(marker.getSnippet());

        //tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }


}