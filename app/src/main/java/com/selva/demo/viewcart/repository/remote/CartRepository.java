package com.selva.demo.viewcart.repository.remote;

import com.selva.demo.viewcart.repository.model.ViewCartResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/28/2018
 */

public class CartRepository {
    private Retrofit mRetrofit;
    private static CartRepository mCartRepository;

    /**
     * Empty private constructor
     */
    private CartRepository() {
    }

    /**
     * Returns the instance of cart repository
     *
     * @return CartRepository
     */
    public static CartRepository getInstance() {
        if (null == mCartRepository) {
            mCartRepository = new CartRepository();
        }
        return mCartRepository;
    }

    /**
     * Initialise the retrofit and returns the view cart api service
     *
     * @return ViewCartAPIService
     */
    private ViewCartAPIService getApiClient() {
        if (null == mRetrofit) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.connectTimeout(60, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ViewCartAPIService.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit.create(ViewCartAPIService.class);
    }

    /**
     * Makes the view cart list web service call
     *
     * @param callback The Retrofit web service call back
     */
    public void getCartDetails(Callback<List<ViewCartResponse>> callback) {
        getApiClient().getViewCartList().enqueue(callback);
    }
}
