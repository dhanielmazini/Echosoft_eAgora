package com.eagora.echosoft.eagora.Maps;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eagora.echosoft.eagora.R;

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

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlaceHolder();
            holder.txtNome = (TextView)row.findViewById(R.id.txtNome);
            holder.imgIcon = (ImageView)row.findViewById(R.id.ic_place_accent_24dp);

            row.setTag(holder);
        }
        else {
            holder = (PlaceHolder)row.getTag();
        }

        Place place = placeList.get(position);
        holder.txtNome.setText(place.getNome());
        holder.imgIcon.setImageResource(R.drawable.ic_place_accent_24dp);

        return row;
    }
    static class PlaceHolder {
        String id;
        TextView txtNome;
        ImageView imgIcon;
    }
}
