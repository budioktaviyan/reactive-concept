package com.baculsoft.reactiveconcept.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
public class RestActivity extends AppCompatActivity implements RestAdapter.ItemClick {
    ProgressBar progressBar;
    Subscription subscription;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    RestAdapter.ItemClick listener;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        initToolbar();
        addItemClickListener();
        initProgressBar();
        initFrameLayout();
        loadCity();
    }

    @Override
    protected void onDestroy() {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count != 0) {
            toolbar.setNavigationIcon(null);
            recyclerView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final String id) {
        final Bundle bundle = new Bundle();
        bundle.putString("id", id);

        final Fragment fragment = DetailFragment.newInstance();
        fragment.setArguments(bundle);

        final String tag = DetailFragment.class.getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.mipmap.ic_arrow_back));
        recyclerView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_rest);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
    }

    private void addItemClickListener() {
        listener = this;
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.pb_rest);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initFrameLayout() {
        frameLayout = (FrameLayout) findViewById(R.id.fl_rest);
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
                final RestAdapter adapter = new RestAdapter(data, listener);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestActivity.this);

                recyclerView = (RecyclerView) findViewById(R.id.rv_rest);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                recyclerView.setAdapter(adapter);
            }
        });
    }
}