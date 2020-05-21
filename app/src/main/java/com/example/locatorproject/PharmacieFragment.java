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
import androidx.fragment.app.FragmentTransaction;
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
import com.example.locatorproject.dummy.DummyPharmacie;
import com.example.locatorproject.dummy.MyLocation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PharmacieFragment extends Fragment {
    private DummyPharmacie m;
    public ArrayList<DummyPharmacie.PharmacieItem> mylist=new ArrayList<DummyPharmacie.PharmacieItem>();
private  MyLocation l=new MyLocation();



    static String insertUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.6714454,-8.008889&radius=3000&types=pharmacy&key=AIzaSyDtrJnZMxWGlXHg34U-Q9ggz1bFKZWnTsc";

    static RequestQueue requestQueue;

double latitude;
double longitude;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PharmacieFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PharmacieFragment newInstance(int columnCount) {
        PharmacieFragment fragment = new PharmacieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_pharmacie_list2, container, false);
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        String s="";

        if (permissionGranted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, new
                    LocationListener() {
                        @Override
                        public void onLocationChanged(android.location.Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            requestQueue = Volley.newRequestQueue(getContext());
                           String pharmacieUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=5000&types=pharmacy&key=AIzaSyDtrJnZMxWGlXHg34U-Q9ggz1bFKZWnTsc";

// Pass data to other Fragment
                            ((MainActivity) getActivity()).message1 = String.valueOf(latitude);
                            ((MainActivity) getActivity()).message2 = String.valueOf(longitude);

                            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                    pharmacieUrl, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray res = response.getJSONArray("results");

                                        for (int i = 0; i < res.length(); i++) {
                                            DummyPharmacie.PharmacieItem pharmacie = new DummyPharmacie.PharmacieItem();
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
                                        MyPharmacieRecyclerViewAdapter.RecyclerViewClickListener mylistener = new MyPharmacieRecyclerViewAdapter.RecyclerViewClickListener() {
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
                                        MyPharmacieRecyclerViewAdapter adapter = new MyPharmacieRecyclerViewAdapter(mylist, mylistener, getActivity());
                                        recyclerView.setAdapter(adapter);



                                    }
                                }}, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());
                                    Log.d("ziko",error.toString());


                                }

                            });

                            Log.d("final",String.valueOf(l.getLatitude()));
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(jsonObjectRequest);
                            Toast.makeText(getActivity(), "you location: "+ longitude+" "+latitude, Toast.LENGTH_SHORT).show();
                            Log.d("lat",String.valueOf(latitude));
                            Log.d("long",String.valueOf(longitude));
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            //buildAlertMessageNoGps();
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }

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
        void onListFragmentInteraction(DummyPharmacie.PharmacieItem item);
    }
}
