package com.baculsoft.reactiveconcept.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskopKota;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder> {
    private final Context mContext;
    private final List<JadwalBioskopKota.Data> mData;
    private final ItemClick mListener;

    public DetailAdapter(final Context context, final List<JadwalBioskopKota.Data> data, final ItemClick listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @Override
    public DetailHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailHolder holder, final int position) {
        final String movie = mData.get(position).movie;
        final String poster = mData.get(position).poster;
        final String genre = mData.get(position).genre;
        final String duration = mData.get(position).duration;

        holder.tvDetailMovie.setText(movie);
        holder.tvDetailGenre.setText(genre);
        holder.tvDetailDuration.setText(duration);
        Glide.with(mContext).load(poster).into(holder.ivDetail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<JadwalBioskopKota.Data> getData() {
        return mData;
    }

    public ItemClick getListener() {
        return mListener;
    }

    class DetailHolder extends RecyclerView.ViewHolder {
        TextView tvDetailMovie;
        TextView tvDetailGenre;
        TextView tvDetailDuration;
        ImageView ivDetail;
        Button btnDetailSchedule;

        public DetailHolder(final View itemView) {
            super(itemView);
            tvDetailMovie = (TextView) itemView.findViewById(R.id.tv_detail_movie);
            tvDetailGenre = (TextView) itemView.findViewById(R.id.tv_detail_genre);
            tvDetailDuration = (TextView) itemView.findViewById(R.id.tv_detail_duration);
            ivDetail = (ImageView) itemView.findViewById(R.id.iv_detail);
            btnDetailSchedule = (Button) itemView.findViewById(R.id.btn_detail_schedule);

            btnDetailSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int position = getAdapterPosition();
                    final List<JadwalBioskopKota.Jadwal> jadwal = getData().get(position).jadwal;
                    getListener().onItemClick(jadwal);
                }
            });
        }
    }

    interface ItemClick {
        void onItemClick(List<JadwalBioskopKota.Jadwal> jadwal);
    }
}