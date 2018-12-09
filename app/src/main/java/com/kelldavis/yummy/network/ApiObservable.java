package com.kelldavis.yummy.network;

import com.kelldavis.yummy.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiObservable {
    @GET(" ")
    Observable<ArrayList<Recipe>> getRecipeResult();
}
