package com.emedinaa.comovil2015.model.response;

import com.emedinaa.comovil2015.model.entity.PokemonEntity;
import com.emedinaa.comovil2015.model.entity.SpeakerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emedinaa on 9/11/14.
 */
public class PokemonResponse implements Serializable {

    private List<PokemonEntity> results;

    public List<PokemonEntity> getResults() {
        return results;
    }

    public void setResults(List<PokemonEntity> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PokemonResponse{" +
                "results=" + results +
                '}';
    }
}
