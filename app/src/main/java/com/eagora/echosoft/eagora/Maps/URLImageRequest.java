package com.eagora.echosoft.eagora.Maps;

/**
 * Created by hhaji on 12/11/17.
 */

public class URLImageRequest {
    public static String RequestURL(String photoRef, int width, int height, Coordenada local) {
        if(!photoRef.equals("SF")) {
            return "https://maps.googleapis.com/maps/api/place/photo?maxheight=" + height +
                    "&maxwidth=" + width +
                    "&photoreference=" + photoRef +
                    "&key=AIzaSyADceanowFmdgs2F1_jnKjyeXCuRa8IrWo";

        }
        else return mapsImageRequestURL(width, height, local);
    }

    public static String mapsImageRequestURL(int width, int height, Coordenada local) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + local.getLatitude() +
                "," + local.getLongitude() + "&maptype=roadmap&markers=color:red%7Clabel:C%7C" +
                local.getLatitude() + "," +
                local.getLongitude() +
                "&zoom=15&scale=2&size=" + width + "x" + height + "&key=AIzaSyADceanowFmdgs2F1_jnKjyeXCuRa8IrWo";
    }
}
