package com.baculsoft.reactiveconcept.views;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskopKota;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class SubDetailAdapter extends RecyclerView.Adapter<SubDetailAdapter.SubDetailHolder> {
    private final List<JadwalBioskopKota.Jadwal> mJadwal;

    public SubDetailAdapter(final List<JadwalBioskopKota.Jadwal> jadwal) {
        mJadwal = jadwal;
    }

    @Override
    public SubDetailHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subdetail, parent, false);
        return new SubDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubDetailHolder holder, final int position) {
        final String theater = mJadwal.get(position).bioskop;
        final String price = mJadwal.get(position).harga;
        final List<String> times = mJadwal.get(position).jam;

        holder.tvSubDetailTheater.setText(theater);
        holder.tvSubDetailTime.setText(TextUtils.join(" , ", times));
        holder.tvSubDetailPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return mJadwal.size();
    }

    class SubDetailHolder extends RecyclerView.ViewHolder {
        TextView tvSubDetailTheater;
        TextView tvSubDetailTime;
        TextView tvSubDetailPrice;

        public SubDetailHolder(final View itemView) {
            super(itemView);
            tvSubDetailTheater = (TextView) itemView.findViewById(R.id.tv_subdetail_theater);
            tvSubDetailTime = (TextView) itemView.findViewById(R.id.tv_subdetail_time);
            tvSubDetailPrice = (TextView) itemView.findViewById(R.id.tv_subdetail_price);
        }
    }
}