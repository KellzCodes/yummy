package com.kelldavis.yummy.utilities;

import com.kelldavis.yummy.network.RetrofitClient;
import com.kelldavis.yummy.network.ApiObservable;

public class ApiUtils {

    private ApiUtils(){}

    public static final String UDACITY_BASE_MOVIE_URL = Constants.UDACITY_BASE_MOVIE_URL;

    public static ApiObservable getApiObservable() {
        return RetrofitClient.getClient(UDACITY_BASE_MOVIE_URL).create(ApiObservable.class);
    }

}
