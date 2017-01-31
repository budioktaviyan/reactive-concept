package com.baculsoft.reactiveconcept.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskopKota;

import org.parceler.Parcels;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class SubDetailDialog extends DialogFragment {
    RecyclerView recyclerView;
    ImageView imageView;

    public static SubDetailDialog newInstance() {
        return new SubDetailDialog();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showSchedule();
        addButtonListener();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_subdetail, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_subdetail);
        imageView = (ImageView) view.findViewById(R.id.iv_subdetail_close);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (null != getDialog().getWindow()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(false);
        }
    }

    private void showSchedule() {
        final Bundle bundle = getArguments();
        final List<JadwalBioskopKota.Jadwal> jadwal = Parcels.unwrap(bundle.getParcelable("jadwal"));
        final SubDetailAdapter adapter = new SubDetailAdapter(jadwal);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.smoothScrollToPosition(recyclerView.getBottom());
        recyclerView.setAdapter(adapter);
    }

    private void addButtonListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                getDialog().dismiss();
            }
        });
    }
}