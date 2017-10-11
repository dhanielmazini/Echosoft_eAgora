package com.eagora.echosoft.eagora.Maps;

import android.net.Uri;
import java.util.List;

/**
 * Created by hhaji on 11/10/17.
 */

public class EnderecoBuilder {

    private StringBuilder endereco;
    private Uri enderecoUri;
    public final String HEADER = "https://www.google.com/maps/dir/?api=1";

    public EnderecoBuilder header() {
        endereco = new StringBuilder();
        endereco.append(HEADER);
        return this;
    }
    public EnderecoBuilder origem(Coordenada origem)
    {
        String origemString = "&origin="+origem.getLatitude()+","+origem.getLongitude()+"&";
        endereco.append(origemString);
        return this;
    }

    public EnderecoBuilder destino(Coordenada destino)
    {
        String destinoString = "destination="+destino.getLatitude()+","+destino.getLongitude();
        endereco.append(destinoString);
        return this;
    }

    public EnderecoBuilder waypoints(List<Coordenada> waypoints)
    {
        endereco.append("&waypoints=");
        for(Coordenada waypoint : waypoints)
        {
            String waypointString = waypoint.getLatitude() + "," + waypoint.getLongitude() + "|";
            endereco.append(waypointString);
        }
        return this;
    }


    public EnderecoBuilder travelMode()
    {
        endereco.append("&travelmode=driving");
        return this;
    }

    public Uri build()
    {
        enderecoUri = Uri.parse(endereco.toString());
        return enderecoUri;
    }
}
