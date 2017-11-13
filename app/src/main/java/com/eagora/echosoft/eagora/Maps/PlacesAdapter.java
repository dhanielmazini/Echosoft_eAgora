package com.eagora.echosoft.eagora.Maps;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hhaji on 30/10/17.
 */
public class PlacesAdapter extends ArrayAdapter<Place> {

    Context context;
    int layoutResourceId;
    List<Place> placeList;

    public PlacesAdapter(Context context, int layoutResourceId,List<Place> list) {
        super(context,0,list);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.placeList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlaceHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlaceHolder();
            holder.txtNome = (TextView)row.findViewById(R.id.txtNome);
            holder.txtNota = (TextView)row.findViewById(R.id.txtNota);
            holder.imgIcon = (ImageView)row.findViewById(R.id.ic_place_accent_24dp);
            holder.imgBanner = (ImageView)row.findViewById(R.id.imgBanner);
            row.setTag(holder);
        }
        else {
            holder = (PlaceHolder)row.getTag();
        }

        Place place = placeList.get(position);
        double nota = place.getNota();
        String notaString = String.valueOf(nota);
        holder.txtNome.setText(place.getNome());
        holder.txtNota.setText(notaString);
        holder.imgIcon.setImageResource(R.drawable.ic_place_accent_24dp);
        String url = URLImageRequest.RequestURL(place.getFoto_ref(), 1000, 1000, place.getLocalizacao());
        Picasso.with(getContext()).load(url).into(holder.imgBanner, new com.squareup.picasso.Callback() {
            Toast toast;
            @Override
            public void onSuccess() {
                toast = Toast.makeText(getContext(), "Sucesso!", Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onError() {
                toast = Toast.makeText(getContext(), "Deu ruim!", Toast.LENGTH_SHORT);
                toast.show();
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
