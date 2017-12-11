package com.eagora.echosoft.eagora.Maps;

import android.net.Uri;

/**
 * Created by hhaji on 25/10/17.
 */

public class PlacesURLBuilder {
    private StringBuilder place;
    private Uri placeUri;
    public final String HEADER = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    public PlacesURLBuilder header() {
        place = new StringBuilder();
        place.append(HEADER);
        return this;
    }

    public PlacesURLBuilder location(Coordenada location) {
        String locationString = "location=" + location.getLatitude() + "," + location.getLongitude();
        place.append(locationString);
        return this;
    }

    public PlacesURLBuilder radius(double radius) {
        String radiusString = "&radius=" + radius;
        place.append(radiusString);
        return this;
    }

    public PlacesURLBuilder keyword(String keyword) {
        String keywordString = "&keyword=" + keyword;
        place.append(keywordString);
        return this;
    }

    public PlacesURLBuilder priceRange(int min, int max) {
        String priceRangeString = "&minprice=" + min + "&maxprice=" + max;
        place.append(priceRangeString);
        return this;
    }

    public PlacesURLBuilder openNow(int open) {
        String openString = "&opennow=" + open;
        place.append(openString);
        return this;
    }

    //tipos em https://developers.google.com/places/web-service/supported_types?hl=pt-br
    public PlacesURLBuilder type(String type) {
        String typeString = "&type=" + type;
        place.append(typeString);
        return this;
    }

    public Uri build() {
        String keyString = "&key=AIzaSyCKjXv6h28YUPQP9JAb6800mlI6355dcmM";
        place.append(keyString);
        placeUri = Uri.parse(place.toString());
        return placeUri;
    }

    public Uri next(String url, String next_page) {
        url = url + "&pagetoken=" + next_page;
        return Uri.parse(url);
    }
}
