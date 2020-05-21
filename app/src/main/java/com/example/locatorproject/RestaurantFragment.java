package com.example.locatorproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.locatorproject.dummy.DummyContent;
import com.example.locatorproject.dummy.DummyContent.DummyItem;
import com.example.locatorproject.dummy.DummyPharmacie;
import com.example.locatorproject.dummy.MyLocation;
import com.example.locatorproject.dummy.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RestaurantFragment extends Fragment {
    private Restaurant m;
    public double longitude;
    public double latitude;
    public ArrayList<Restaurant.RestaurantItem> mylist=new ArrayList<Restaurant.RestaurantItem>();
    private MyLocation l=new MyLocation();



    static String insertUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.6714454,-8.008889&radius=3000&types=pharmacy&key=AIzaSyDtrJnZMxWGlXHg34U-Q9ggz1bFKZWnTsc";

    static RequestQueue requestQueue;



    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantFragment() {
    }

    // TODO: Customize parameter initialization


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        Bundle bundle = this.getArguments();
        String latitudetext = "";
        String longitudetext = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        Bundle bundle = this.getArguments();
        String latitudetext=((MainActivity) getActivity()).message1;
        latitude =Double.parseDouble(latitudetext);
        String longitudetext=((MainActivity) getActivity()).message2;
        longitude =Double.parseDouble(longitudetext);


                            requestQueue = Volley.newRequestQueue(getContext());
                            String pharmacieUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=5000&types=restaurant&key=AIzaSyDtrJnZMxWGlXHg34U-Q9ggz1bFKZWnTsc";

                            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                    pharmacieUrl, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray res = response.getJSONArray("results");

                                        for (int i = 0; i < res.length(); i++) {
                                            Restaurant.RestaurantItem pharmacie = new Restaurant.RestaurantItem();
                                            pharmacie.setId(String.valueOf(i+1));
                                            pharmacie.setLng(res.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                                            pharmacie.setLat(res.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                                            pharmacie.setName(res.getJSONObject(i).getString("name"));
                                            pharmacie.setVicinity(res.getJSONObject(i).getString("vicinity"));
                                            double d2r = Math.PI / 180;
                                            double dlong = (longitude - pharmacie.getLng()) * d2r;
                                            double dlat = (latitude - pharmacie.getLat()) * d2r;
                                            double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(pharmacie.getLat() * d2r)
                                                    * Math.cos(latitude * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
                                            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                                            double d = 6367 * c;
                                            pharmacie.setDistance(Math.floor(d));

                                            mylist.add(pharmacie);


                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    if (view instanceof RecyclerView) {
                                        Context context = view.getContext();
                                        final RecyclerView recyclerView = (RecyclerView) view;


                                        if (mColumnCount <= 1) {
                                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        } else {
                                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                        }
                                        MyRestaurantRecyclerViewAdapter.RecyclerViewClickListener mylistener = new MyRestaurantRecyclerViewAdapter.RecyclerViewClickListener() {
                                            @Override
                                            public void onClick(View view, int position) {
                                                Intent intent = new Intent(getActivity(), MapsActivity.class);
                                                Bundle bundle = new Bundle();
                                                intent.putExtra("mylat", latitude + "");
                                                intent.putExtra("mylon", longitude+ "");
                                                intent.putExtra("lat", mylist.get(position).getLat() + "");
                                                intent.putExtra("lon", mylist.get(position).getLng()+ "");

                                                startActivity(intent);
                                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                                            }


                                        };
                                        MyRestaurantRecyclerViewAdapter adapter = new MyRestaurantRecyclerViewAdapter(mylist, mylistener, getActivity());
                                        recyclerView.setAdapter(adapter);



                                    }
                                }}, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());



                                }

                            });

                            Log.d("final",String.valueOf(l.getLatitude()));
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(jsonObjectRequest);
                            Toast.makeText(getActivity(), " your location: "+ longitude+" "+latitude, Toast.LENGTH_SHORT).show();
                            Log.d("lat",String.valueOf(latitude));
                            Log.d("long",String.valueOf(longitude));


        return view;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Restaurant.RestaurantItem item);
    }
}
