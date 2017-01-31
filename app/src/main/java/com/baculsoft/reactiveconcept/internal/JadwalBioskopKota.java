package com.baculsoft.reactiveconcept.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JadwalBioskopKota {

    @JsonProperty(value = "status")
    public String status;

    @JsonProperty(value = "kota")
    public String kota;

    @JsonProperty(value = "date")
    public String date;

    @JsonProperty(value = "data")
    public List<Data> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty(value = "movie")
        public String movie;

        @JsonProperty(value = "poster")
        public String poster;

        @JsonProperty(value = "genre")
        public String genre;

        @JsonProperty(value = "duration")
        public String duration;

        @JsonProperty(value = "jadwal")
        public List<Jadwal> jadwal;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Parcel(Parcel.Serialization.BEAN)
    public static class Jadwal {

        @JsonProperty(value = "bioskop")
        public String bioskop;

        @JsonProperty(value = "jam")
        public List<String> jam;

        @JsonProperty(value = "harga")
        public String harga;
    }
}