package com.selva.demo.viewcart.repository.remote;

import com.selva.demo.viewcart.repository.model.ViewCartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/2/2018
 */

public interface ViewCartAPIService {
    String BASE_URL = "http://www.mocky.io/";

    @GET("v2/5b3d1da83100002c006de010")
    Call<List<ViewCartResponse>> getViewCartList();
}
