package com.emedinaa.monkeyexample.model.response;

import com.emedinaa.monkeyexample.model.entity.PokemonEntity;

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
