package com.baculsoft.reactiveconcept.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JadwalBioskop {

    @JsonProperty(value = "data")
    public List<Data> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty(value = "id")
        public String id;

        @JsonProperty(value = "kota")
        public String kota;
    }
}