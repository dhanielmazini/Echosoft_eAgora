package com.eagora.echosoft.eagora.Maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hhaji on 30/10/17.
 */
public class PlacesAdapter extends ArrayAdapter<Place> {

    Context context;
    int layoutResourceId;
    List<Place> placeList;
    private View row;
    private int flag = 0;

    public PlacesAdapter(Context context, int layoutResourceId, List<Place> list, int flag) {
        super(context,0,list);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.placeList = list;
        this.flag = flag;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        row = convertView;
        PlaceHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlaceHolder();
            holder.txtNome = (TextView)row.findViewById(R.id.txtNome);
            holder.txtNota = (TextView)row.findViewById(R.id.txtNota);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.imgBanner = (ImageView)row.findViewById(R.id.imgBanner);
            row.setTag(holder);
        }
        else {
            holder = (PlaceHolder)row.getTag();
        }

        Place place = placeList.get(position);
        final String id = place.getId();
        double nota = place.getNota();
        String notaString = String.valueOf(nota);
        holder.txtNome.setText(place.getName());
        holder.txtNota.setText(notaString);
        Picasso.with(getContext()).load(place.getIcone()).into(holder.imgIcon);
        String url = URLImageRequest.RequestURL(place.getFoto_ref(), 1000, 1000, place.getLocalizacao());
        Picasso.with(getContext()).load(url).into(holder.imgBanner, new com.squareup.picasso.Callback() {
            Toast toast;
            @Override
            public void onSuccess() {
                toast = Toast.makeText(getContext(), "Sucesso!", Toast.LENGTH_SHORT);
                //toast.show();
            }
            @Override
            public void onError() {
                toast = Toast.makeText(getContext(), "Deu ruim!", Toast.LENGTH_SHORT);
                //toast.show();
            }
        });

        final ImageView imageView = (ImageView) row.findViewById(R.id.imgBanner);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act = new Intent(view.getContext(), PlaceActivity.class);
                try {
                    act.putExtra("PlaceObj", placeList.get(position));
                    act.putExtra("flag", flag);
                    context.startActivity(act);
                }
                catch(Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        });

        return row;
    }
    static class PlaceHolder {
        String id;
        TextView txtNome;
        TextView txtNota;
        ImageView imgIcon;
        ImageView imgBanner;
    }
}
