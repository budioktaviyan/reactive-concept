package com.baculsoft.reactiveconcept.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskop;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class RestAdapter extends RecyclerView.Adapter<RestAdapter.RestHolder> {
    private final List<JadwalBioskop.Data> mData;
    private final ItemClick mListener;

    public RestAdapter(final List<JadwalBioskop.Data> data, final ItemClick listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public RestHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rest, parent, false);
        return new RestHolder(view);
    }

    @Override
    public void onBindViewHolder(final RestHolder holder, final int position) {
        final String id = mData.get(position).id;
        final String city = mData.get(position).kota;

        holder.tvRest.setTag(id);
        holder.tvRest.setText(city);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public ItemClick getListener() {
        return mListener;
    }

    class RestHolder extends RecyclerView.ViewHolder {
        TextView tvRest;

        public RestHolder(final View itemView) {
            super(itemView);
            tvRest = (TextView) itemView.findViewById(R.id.tv_rest);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final String id = tvRest.getTag().toString();
                    getListener().onItemClick(id);
                }
            });
        }
    }

    interface ItemClick {
        void onItemClick(String id);
    }
}