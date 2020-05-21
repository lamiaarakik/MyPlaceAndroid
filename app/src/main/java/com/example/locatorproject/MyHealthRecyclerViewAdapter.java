package com.example.locatorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.locatorproject.dummy.Health;
import com.example.locatorproject.dummy.Restaurant;

import java.util.List;

public class MyHealthRecyclerViewAdapter  extends RecyclerView.Adapter<MyHealthRecyclerViewAdapter.ViewHolder> {
    private final List<Health.HealthItem> mValues;

    private MyHealthRecyclerViewAdapter.RecyclerViewClickListener myListener;
    private final Context mContext;

    public MyHealthRecyclerViewAdapter(List<Health.HealthItem> items, MyHealthRecyclerViewAdapter.RecyclerViewClickListener listener, Context mContext) {
        mValues = items;
        myListener = listener;
        this.mContext = mContext;
    }



    @Override
    public MyHealthRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant, parent, false);
        return new MyHealthRecyclerViewAdapter.ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(final MyHealthRecyclerViewAdapter.ViewHolder holder, final int position) {
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
        private MyHealthRecyclerViewAdapter.RecyclerViewClickListener mListener;

        public Health.HealthItem mItem;


        public ViewHolder(View view, MyHealthRecyclerViewAdapter.RecyclerViewClickListener listener) {
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
