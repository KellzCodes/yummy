package com.kelldavis.yummy.network;

import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.utilities.ApiUtils;

import java.util.ArrayList;

import io.reactivex.Observable;

public class NetworkService {

    private ApiObservable mApiObservable;

    public NetworkService(){
        mApiObservable = ApiUtils.getApiObservable();
    }

    public Observable<ArrayList<Recipe>> networkApiRequestRecipes() {
        return mApiObservable.getRecipeResult();
    }
}
