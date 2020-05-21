package com.example.locatorproject;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locatorproject.RestaurantFragment.OnListFragmentInteractionListener;
import com.example.locatorproject.dummy.DummyContent.DummyItem;
import com.example.locatorproject.dummy.DummyPharmacie;
import com.example.locatorproject.dummy.Restaurant;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant.RestaurantItem> mValues;

    private RecyclerViewClickListener myListener;
    private final Context mContext;

    public MyRestaurantRecyclerViewAdapter(List<Restaurant.RestaurantItem> items, RecyclerViewClickListener listener, Context mContext) {
        mValues = items;
        myListener = listener;
        this.mContext = mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant, parent, false);
        return new ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mNomView.setText(mValues.get(position).name);

        holder.mDisView.setText(String.valueOf(mValues.get(position).distance));

       /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != myListener) {


                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNomView;

        public final TextView mDisView;
        private RecyclerViewClickListener mListener;

        public Restaurant.RestaurantItem mItem;


        public ViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mNomView = (TextView) view.findViewById(R.id.name);

            mDisView = (TextView) view.findViewById(R.id.distance);
            mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNomView.getText() + "'";
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}
