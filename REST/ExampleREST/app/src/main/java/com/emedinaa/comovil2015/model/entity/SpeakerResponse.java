package com.emedinaa.comovil2015.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emedinaa on 9/11/14.
 */
public class SpeakerResponse implements Serializable {

    private List<SpeakerEntity> results;

    public List<SpeakerEntity> getResults() {
        return results;
    }

    public void setResults(List<SpeakerEntity> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SpeakerResponseEntity{" +
                "results=" + results +
                '}';
    }
}
