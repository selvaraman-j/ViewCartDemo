package com.selva.demo.viewcart.repository.remote;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.model.ViewCartResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
     * Returns the mutable live data of view cart model list
     *
     * @return the live data view cart model listMutableLiveData<List<ViewCartModel>>
     */
    public MutableLiveData<List<ViewCartModel>> getCartDetails() {
        final MutableLiveData<List<ViewCartModel>> mutableLiveData = new MutableLiveData<>();
        getApiClient().getViewCartList().enqueue(new Callback<List<ViewCartResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ViewCartResponse>> call, @NonNull Response<List<ViewCartResponse>> response) {
                if (response.isSuccessful()) {
                    List<ViewCartResponse> viewCartResponseList = response.body();
                    if (null != viewCartResponseList) {
                        mutableLiveData.setValue(ViewCartModel.getViewCartModelList(viewCartResponseList));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ViewCartResponse>> call, @NonNull Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
