package com.eagora.echosoft.eagora.Maps;

import android.net.Uri;

import java.util.List;

/**
 * Created by hhaji on 25/10/17.
 */

public class PlacesBuilder {
    private StringBuilder place;
    private Uri placeUri;
    public final String HEADER = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    public PlacesBuilder header() {
        place = new StringBuilder();
        place.append(HEADER);
        return this;
    }

    public PlacesBuilder location(Coordenada location) {
        String locationString = "location=" + location.getLatitude() + "," + location.getLongitude();
        place.append(locationString);
        return this;
    }

    public PlacesBuilder radius(double radius) {
        String radiusString = "&radius=" + radius;
        place.append(radiusString);
        return this;
    }

    public PlacesBuilder keyword(String keyword) {
        String keywordString = "&keyword=" + keyword;
        place.append(keywordString);
        return this;
    }

    public PlacesBuilder priceRange(int min, int max) {
        String priceRangeString = "&minprice=" + min + "&maxprice=" + max;
        place.append(priceRangeString);
        return this;
    }

    public PlacesBuilder openNow(int open) {
        String openString = "&opennow=" + open;
        place.append(openString);
        return this;
    }

    //tipos em https://developers.google.com/places/web-service/supported_types?hl=pt-br
    public PlacesBuilder type(String type) {
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
