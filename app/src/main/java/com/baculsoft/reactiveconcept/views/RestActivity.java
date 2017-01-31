package com.baculsoft.reactiveconcept.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.baculsoft.reactiveconcept.R;
import com.baculsoft.reactiveconcept.internal.JadwalBioskop;
import com.baculsoft.reactiveconcept.utils.Connections;
import com.baculsoft.reactiveconcept.utils.Constants;

import java.util.List;

import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class RestActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Subscription subscription;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        initToolbar();
        initProgressBar();
        loadCity();
    }

    @Override
    protected void onDestroy() {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rest);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.pb_result);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadCity() {
        subscription = Connections.get().getApi().jadwalBioskop(Constants.KEY).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response<JadwalBioskop>>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(final Response<JadwalBioskop> response) {
                final List<JadwalBioskop.Data> data = response.body().data;
                final RestAdapter adapter = new RestAdapter(data);
                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_result);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestActivity.this);

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                recyclerView.setAdapter(adapter);
            }
        });
    }
}