package com.example.locatorproject.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Health{
public static final List<HealthItem> ITEMS = new ArrayList<HealthItem>();

public static final Map<String, HealthItem> ITEM_MAP = new HashMap<String, HealthItem>();
private static final int COUNT = 25;

private static void addItem(HealthItem pharmacie) {
        ITEMS.add(pharmacie);
        ITEM_MAP.put(pharmacie.id, pharmacie);
        }
    /* private static PharmacieItem createPharmacieItem(int position) {
         return new PharmacieItem(String.valueOf(position), "Item " + position, makeDetails(position));
     }*/
private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
        builder.append("\nMore details information here.");
        }
        return builder.toString();
        }
public static class HealthItem{
    public String id;
    public String name;
    public double lat;
    public double lng;
    public String icon;
    public String vicinity;
    public double rating;
    public double distance;

    public HealthItem() {
    }

    public HealthItem(String id, String name, double lat, double lng, String icon, String vicinity, double rating, double distance) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.vicinity = vicinity;
        this.rating = rating;
        this.distance = distance;
    }

    public HealthItem(String id, String name, String vicinity,double distance,double lat, double lng) {
        this.id = id;
        this.name = name;
        this.vicinity = vicinity;
        this.distance=distance;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


}

}


