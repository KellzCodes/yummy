/*
 * Copyright (C) 2018 Kelli Davis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kelldavis.yummy.utilities;

import com.kelldavis.yummy.network.RetrofitClient;
import com.kelldavis.yummy.network.ApiObservable;

public class ApiUtils {

    private ApiUtils(){}

    public static final String BASE_MOVIE_URL = Constants.UDACITY_BASE_MOVIE_URL;

    public static ApiObservable getApiObservable() {
        return RetrofitClient.getClient(BASE_MOVIE_URL).create(ApiObservable.class);
    }

}
