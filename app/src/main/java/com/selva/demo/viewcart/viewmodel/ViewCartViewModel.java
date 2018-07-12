package com.selva.demo.viewcart.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.selva.demo.viewcart.R;
import com.selva.demo.viewcart.repository.database.ViewCartDatabase;
import com.selva.demo.viewcart.repository.database.ViewCartDatabaseHandler;
import com.selva.demo.viewcart.repository.database.entity.ViewCartModelEntity;
import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.model.ViewCartResponse;
import com.selva.demo.viewcart.repository.remote.CartRepository;
import com.selva.demo.viewcart.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/28/2018
 */

public class ViewCartViewModel extends AndroidViewModel {
    private MutableLiveData<List<ViewCartModel>> mLiveData = new MutableLiveData<>();
    private ViewCartDatabase mViewCartDatabase;
    private boolean isVeryFirstTime = true;

    public ViewCartViewModel(@NonNull Application application) {
        super(application);
        mViewCartDatabase = ViewCartDatabase.getInstance(getApplication());
    }

    /**
     * Returns the cart view live data from db. If the cart view is empty/null or
     * force update is true then fetch the data's from server.
     *
     * @return LiveData the list of cart view live data
     */
    public LiveData<List<ViewCartModel>> getCartListData(boolean isForceUpdate) {
        //If force update then will fetch the data from server
        if (isForceUpdate) {
            fetchDataFromServer();
        } else {
            //Retrieves the value from db
            new ViewCartDatabaseHandler.RetrieveCartList(mViewCartDatabase, (List<ViewCartModelEntity> listLiveData) -> {
                //No values in db so fetching data from web service
                // to avoid the infinite loop adding network check
                if ((null == listLiveData || listLiveData.size() == 0
                        && isVeryFirstTime && NetworkUtils.isNetworkConnected(getApplication()))) {
                    fetchDataFromServer();
                } else {
                    // updates the value from db
                    mLiveData.setValue(ViewCartModel.getDbViewCartModelList(listLiveData));
                }
                // to restrict the web service call after removed all cart items
                isVeryFirstTime = false;
            }).execute();
        }

        return mLiveData;
    }

    /**
     * Fetches the cart view list items from server.
     * If the device is not connected to internet then will return
     * the persisted cart view list items from db
     */
    private void fetchDataFromServer() {
        if (!NetworkUtils.isNetworkConnected(getApplication())) {
            getCartListData(false);
            Toast.makeText(getApplication()
                    , getApplication().getString(R.string.no_internet_connection)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        //Cart view retrofit callback
        CartRepository.getInstance().getCartDetails(new Callback<List<ViewCartResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ViewCartResponse>> call
                    , @NonNull Response<List<ViewCartResponse>> response) {
                if (response.isSuccessful()) {
                    List<ViewCartResponse> viewCartResponseList = response.body();
                    if (null != viewCartResponseList) {
                        mLiveData.setValue(ViewCartModel.getViewCartModelList(viewCartResponseList));
                        //add cart list items into the db
                        addCartToDb(viewCartResponseList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ViewCartResponse>> call, @NonNull Throwable t) {
                //Web service call failed
            }
        });
    }

    /**
     * Adds the cart view web service response into the db
     *
     * @param viewCartResponseList the List<ViewCartResponse>
     */
    private void addCartToDb(List<ViewCartResponse> viewCartResponseList) {
        new ViewCartDatabaseHandler.AddCartList(mViewCartDatabase
                , ViewCartModelEntity.getViewCartModelEntity(viewCartResponseList)).execute();
    }

    /**
     * Update the cart view items in db
     */
    public void updateViewCart(ViewCartModel viewCartModel) {
        if (null != viewCartModel) {
            new ViewCartDatabaseHandler.UpdateCartItem(mViewCartDatabase
                    , ViewCartModelEntity.getViewCartModelEntity(viewCartModel)
                    , this).execute();
        }
    }

    /**
     * Remove the particular cart view item from db
     *
     * @param viewCartModel The ViewCartModel to be removed from db
     */
    public void removeViewCartItems(ViewCartModel viewCartModel) {
        if (null != viewCartModel) {
            new ViewCartDatabaseHandler.DeleteCartItem(mViewCartDatabase
                    , ViewCartModelEntity.getViewCartModelEntity(viewCartModel)
                    , this).execute();
        }
    }
}
