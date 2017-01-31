package com.baculsoft.reactiveconcept.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskopKota;
import com.baculsoft.reactiveconcept.utils.Connections;
import com.baculsoft.reactiveconcept.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class DetailFragment extends Fragment implements DetailAdapter.ItemClick {
    ProgressBar progressBar;
    Subscription subscription;
    DetailAdapter.ItemClick listener;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initProgressBar();
        addItemClickListener();
        loadMovie();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onDestroyView() {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        super.onDestroyView();
    }

    @Override
    public void onItemClick(final List<JadwalBioskopKota.Jadwal> jadwal) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable("jadwal", Parcels.wrap(jadwal));

        final DialogFragment dialogFragment = SubDetailDialog.newInstance();
        dialogFragment.setArguments(bundle);

        final String tag = SubDetailDialog.class.getSimpleName();
        dialogFragment.show(getFragmentManager(), tag);
    }

    public void initViewGroup() {
        final LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.ll_detail);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) getActivity().findViewById(R.id.pb_detail);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void addItemClickListener() {
        listener = this;
    }

    private void loadMovie() {
        final Bundle bundle = getArguments();
        final String id = bundle.getString("id");

        subscription = Connections.get().getApi().jadwalBioskopKota(id, Constants.KEY).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response<JadwalBioskopKota>>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(final Response<JadwalBioskopKota> response) {
                if (null != response && !response.body().status.equalsIgnoreCase("error")) {
                    final String city = response.body().kota;
                    final String date = response.body().date;

                    final TextView tvDetailCity = (TextView) getActivity().findViewById(R.id.tv_detail_city);
                    final TextView tvDetailDate = (TextView) getActivity().findViewById(R.id.tv_detail_date);

                    tvDetailCity.setText(city);
                    tvDetailDate.setText(date);

                    final List<JadwalBioskopKota.Data> data = response.body().data;
                    final DetailAdapter adapter = new DetailAdapter(getContext(), data, listener);
                    final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_detail);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                    recyclerView.setAdapter(adapter);

                    initViewGroup();
                } else {
                    Toast.makeText(getContext(), "No data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}