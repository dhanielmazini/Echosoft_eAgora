package com.eagora.echosoft.eagora.Maps;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hhaji on 11/10/17.
 */

public class CoordenadaListFactory {
    public static List<Coordenada> createCoordenadas (List<String> enderecos, AppCompatActivity activity)
    {
        Geocoder geoCoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());// esse Geocoder aqui é quem vai traduzir o endereço de String para coordenadas double
        List<Address> addresses = null;//este Adress aqui recebe um retorno do metodo geoCoder.getFromLocationName vc manipula este retorno pra pega as coordenadas
        List<Coordenada> coordenadas = new ArrayList<>();
        double latitude, longitude;

        for(String endereco : enderecos) {
            try {
                addresses = geoCoder.getFromLocationName(endereco, 1);// o numero um aqui é a quantidade maxima de resultados que vc quer receber


            } catch (IOException e) {
                //TODO
            }
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
            Coordenada coordenada = new Coordenada(latitude, longitude);
            coordenadas.add(coordenada);
        }
        return coordenadas;
    }
}
