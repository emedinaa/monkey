package com.emedinaa.monkeyexample.model.response;

import com.emedinaa.monkeyexample.model.entity.TypeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emedinaa on 9/11/14.
 */
public class TypePokemonResponse implements Serializable {

    private List<TypeEntity> results;

    public List<TypeEntity> getResults() {
        return results;
    }

    public void setResults(List<TypeEntity> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "TypePokemonResponse{" +
                "results=" + results +
                '}';
    }
}
