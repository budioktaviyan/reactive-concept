package com.baculsoft.reactiveconcept.internal;

import com.baculsoft.reactiveconcept.utils.Constants;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public interface Api {

    @GET(Constants.Api.JADWAL_BIOSKOP)
    Observable<Response<JadwalBioskop>> jadwalBioskop(@Query("k") String key);

    @GET(Constants.Api.JADWAL_BIOSKOP)
    Observable<Response<JadwalBioskopKota>> jadwalBioskopKota(@Query("id") String id,
                                                              @Query("k") String key);
}